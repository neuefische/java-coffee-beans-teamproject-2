package com.example.backend.controller;

import com.example.backend.model.Actor;
import com.example.backend.model.ActorRepository;
import com.example.backend.model.Movie;
import com.example.backend.model.MovieRepository;
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
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ActorControllerTest {
    private static final String URL_BASE = "/api/actor";
    private static final String URL_AUTOCOMPLETION = "/api/actor/autocompletion/{prefix}";
    private static final String URL_GET_BY_MOVIE = "/api/actor/movie/{movieId}";

    private static final long ID_FIRST = 1L;
    private static final long ID_SECOND = 2L;

    private static final String NAME_JANE = "Jane Doe";
    private static final String NAME_JIM = "Jim Doe";
    private static final String NAME_JOE = "Joe Doe";
    private static final String NAME_JOHN = "John Doe";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MovieRepository movieRepository;

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
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<Actor> actual = actorRepository.findAll();
        List<Actor> expected = List.of(Actor.builder().id(ID_FIRST).movies(Set.of()).name(NAME_JOHN).build());
        assertEquals(expected, actual);
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
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<Actor> actual = actorRepository.findAll();
        List<Actor> expected = List.of(Actor.builder().id(ID_FIRST).movies(Set.of()).name(NAME_JANE).build());
        assertEquals(expected, actual);
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
                        ))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        List<Actor> actual = actorRepository.findAll();
        List<Actor> expected = List.of();
        assertEquals(expected, actual);
    }

    @Test
    void get() {
    }

    @Test
    void update() {
    }

    @Test
    void getAll() {
    }

    @Test
    void delete() {
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

        mvc.perform(MockMvcRequestBuilders.get(URL_AUTOCOMPLETION, "Jo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(NAME_JOE))
                .andExpect(jsonPath("$[1].name").value(NAME_JOHN));
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

        mvc.perform(MockMvcRequestBuilders.get(URL_AUTOCOMPLETION, "Ji"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DirtiesContext
    void getByNameTest_emptyRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL_AUTOCOMPLETION, ""))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DirtiesContext
    void getByMovieIdTest_multipleMatch() throws Exception {
        Movie movieFirst = Movie.builder().name("Some Movie Name First").build();
        Movie movieSecond = Movie.builder().name("Some Movie Name Second").build();

        movieRepository.saveAll(List.of(movieFirst, movieSecond));

        actorRepository.saveAll(
                List.of(
                        Actor.builder().name(NAME_JANE).movies(Set.of(movieFirst)).build(),
                        Actor.builder().name(NAME_JIM).movies(Set.of(movieFirst, movieSecond)).build(),
                        Actor.builder().name(NAME_JOE).movies(Set.of(movieSecond)).build(),
                        Actor.builder().name(NAME_JOHN).movies(Set.of()).build()
                )
        );

        mvc.perform(MockMvcRequestBuilders.get(URL_GET_BY_MOVIE, movieSecond.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(NAME_JIM))
                .andExpect(jsonPath("$[1].name").value(NAME_JOE));
    }

    @Test
    @DirtiesContext
    void getByMovieIdTest_noMatch() throws Exception {
        Movie movieFirst = Movie.builder().name("Some Movie Name First").build();
        Movie movieSecond = Movie.builder().name("Some Movie Name Second").build();

        movieRepository.saveAll(List.of(movieFirst, movieSecond));

        actorRepository.saveAll(
                List.of(
                        Actor.builder().name(NAME_JANE).movies(Set.of(movieFirst)).build(),
                        Actor.builder().name(NAME_JIM).movies(Set.of(movieFirst)).build(),
                        Actor.builder().name(NAME_JOE).movies(Set.of(movieFirst)).build(),
                        Actor.builder().name(NAME_JOHN).movies(Set.of()).build()
                )
        );

        mvc.perform(MockMvcRequestBuilders.get(URL_GET_BY_MOVIE, movieSecond.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getByMovieIdTest_emptyRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(URL_GET_BY_MOVIE, ""))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}