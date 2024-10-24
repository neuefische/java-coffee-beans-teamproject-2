package com.example.backend.controller;

import com.example.backend.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ActorControllerTest {
    private static final String URL_BASE = "/api/actor";
    private static final String URL_AUTOCOMPLETION = "/api/actor/autocompletion/{prefix}";

    private static final String ID_FIRST = "1L";
    private static final String ID_SECOND = "2L";

    private static final String NAME_JANE = "Jane Doe";
    private static final String NAME_JIM = "Jim Doe";
    private static final String NAME_JOE = "Joe Doe";
    private static final String NAME_JOHN = "John Doe";

    private SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockUser() {
        return oidcLogin().userInfoToken(token -> token
                .claim("login", NAME_JOHN));
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ActorRepository actorRepository;

    @Test
    @DirtiesContext
    void saveTest_correct() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                 {
                                      "name": "%s"
                                 }
                                """.formatted(NAME_JOHN)
                )
                .with(mockUser())).andExpect(MockMvcResultMatchers.status().isOk());

        List<Actor> actual = actorRepository.findAll();
        assertEquals(1, actual.size());
        assertEquals(NAME_JOHN, actual.getFirst().getName());
    }
    @Test
    @DirtiesContext
    void saveTest_Unauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "name": "%s"
                                         }
                                        """.formatted(NAME_JOHN)
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    void saveTest_correct_idIgnored() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                         {
                                                "id": "%s",
                                                "name": "%s"
                                         }
                                """.formatted(ID_SECOND, NAME_JANE)
                )
                .with(mockUser())).andExpect(MockMvcResultMatchers.status().isOk());

        List<Actor> actual = actorRepository.findAll();
        assertEquals(1, actual.size());
        assertEquals(NAME_JANE, actual.getFirst().getName());
        assertNotEquals(ID_SECOND, actual.getFirst().getId());
    }

    @Test
    @DirtiesContext
    void saveTest_incorrectNoName() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                         {
                                                "id": "%s"
                                         }
                                """.formatted(ID_FIRST)
                )
                .with(mockUser())).andExpect(MockMvcResultMatchers.status().is4xxClientError());

        List<Actor> actual = actorRepository.findAll();
        List<Actor> expected = List.of();
        assertEquals(expected, actual);
    }


    @Test
    @DirtiesContext
    void getByNameTest_multipleMatch() throws Exception {

        actorRepository.saveAll(
                List.of(
                        Actor.builder().name(NAME_JANE).build(),
                        Actor.builder().name(NAME_JIM).build(),
                        Actor.builder().name(NAME_JOE).build(),
                        Actor.builder().name(NAME_JOHN).build()
                )
        );

        mvc.perform(MockMvcRequestBuilders.get(URL_AUTOCOMPLETION, "Jo")
                        .with(mockUser())).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(NAME_JOE))
                .andExpect(jsonPath("$[1].name").value(NAME_JOHN));
    }
    @Test
    @DirtiesContext
    void get_Unauthorized() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get(URL_AUTOCOMPLETION, "Jo")
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    void getByNameTest_noMatch() throws Exception {
        actorRepository.saveAll(
                List.of(
                        Actor.builder().name(NAME_JANE).build(),
                        Actor.builder().name(NAME_JOE).build(),
                        Actor.builder().name(NAME_JOHN).build()
                )
        );

        mvc.perform(MockMvcRequestBuilders.get(URL_AUTOCOMPLETION, "Ji")
                        .with(mockUser())).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DirtiesContext
    void getByNameTest_emptyRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL_AUTOCOMPLETION, "")
                .with(mockUser())).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}