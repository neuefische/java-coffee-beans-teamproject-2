package com.example.backend.controller;

import com.example.backend.model.Director;
import com.example.backend.model.DirectorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class DirectorControllerTest {
    private static final String URL_BASE = "/api/director";
    private static final long ID_FIRST = 1L;
    private static final String DIRECTOR_ONE = "Guy Ritchie";
    private static final String DIRECTOR_TWO = "Quentin Tarantino";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DirectorRepository directorRepository;

    @Test
    @DirtiesContext
    void save() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "name": "%s"
                                        }
                                        """.formatted(DIRECTOR_ONE)
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk());
        List<Director> actualDirector = directorRepository.findAll();

        assertEquals(1, actualDirector.size());
        assertEquals(DIRECTOR_ONE, actualDirector.getFirst().getName());
    }

    @Test
    @DirtiesContext
    void save_NoName_Provided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "name": ""
                                        }
                                        """
                        ))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DirtiesContext
    void getAll() throws Exception {
        directorRepository.saveAll(
                List.of(
                        Director.builder().name(DIRECTOR_ONE).build(),
                        Director.builder().name(DIRECTOR_TWO).build()
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(DIRECTOR_ONE))
                .andExpect(jsonPath("$[1].name").value(DIRECTOR_TWO));

    }

    @Test
    @DirtiesContext
    void getAll_Empty() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DirtiesContext
    void getDirectorById() throws Exception {
        Director firstDirector = Director.builder().name(DIRECTOR_ONE).build();
        Director secondDirector = Director.builder().name(DIRECTOR_TWO).build();

        directorRepository.saveAll(
                List.of(
                        firstDirector, secondDirector
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" + firstDirector.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(DIRECTOR_ONE))
                .andExpect(jsonPath("$.id").value(firstDirector.getId()));

    }

    @Test
    @DirtiesContext
    void getDirectorById_NonExisting_ID() throws Exception {
        directorRepository.saveAll(
                List.of(
                        Director.builder().name(DIRECTOR_ONE).build(),
                        Director.builder().name(DIRECTOR_TWO).build()
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" + 3))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    @DirtiesContext
    void delete_Sucessfull() throws Exception {
        Director firstDirector = Director.builder().name(DIRECTOR_ONE).build();
        Director secondDirector = Director.builder().name(DIRECTOR_TWO).build();

        directorRepository.saveAll(
                List.of(
                        firstDirector,
                        secondDirector
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE + "/" + firstDirector.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(DIRECTOR_TWO));
    }


    @Test
    @DirtiesContext
    void delete_NonExisting_ID() throws Exception {
        directorRepository.saveAll(
                List.of(
                        Director.builder().name(DIRECTOR_ONE).build(),
                        Director.builder().name(DIRECTOR_TWO).build()
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE + "/" + 3))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }
}