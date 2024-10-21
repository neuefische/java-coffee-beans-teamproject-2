package com.example.backend.controller;

import com.example.backend.model.*;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class MovieDirectorControllerTest {
    private static final String URL_BASE = "/api/movie-director";
    private static final String URL_WITH_ID = "/api/movie-director/{id}";

    private static final String DIRECTOR_NAME_JANE = "Jane Doe";
    private static final String DIRECTOR_NAME_JIM = "Jim Doe";
    private static final String DIRECTOR_NAME_JOE = "Joe Doe";

    private static final String MOVIE_NAME_MEMENTO = "Memento";
    private static final String MOVIE_NAME_DEADPOOL = "Deadpool";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private MovieDirectorRelationRepository movieDirectorRelationRepository;

    @Test
    @DirtiesContext
    void addRelation() throws Exception {
        Movie movieFirst = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Movie movieSecond = Movie.builder().name(MOVIE_NAME_DEADPOOL).build();
        Director directorJane = Director.builder().name(DIRECTOR_NAME_JANE).build();
        Director directorJim = Director.builder().name(DIRECTOR_NAME_JIM).build();

        movieRepository.saveAll(List.of(movieFirst, movieSecond));
        directorRepository.saveAll(List.of(directorJane, directorJim));
        movieDirectorRelationRepository.saveAll(List.of(
                MovieDirectorRelation.builder().directorId(directorJim.getId()).movieId(movieFirst.getId()).build(),
                MovieDirectorRelation.builder().directorId(directorJim.getId()).movieId(movieSecond.getId()).build(),
                MovieDirectorRelation.builder().directorId(directorJane.getId()).movieId(movieSecond.getId()).build()
        ));
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "movieId": "%s",
                                              "directorId": "%s"
                                         }
                                        """.formatted(movieFirst.getId(), directorJane.getId())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
        Long relationCount = movieDirectorRelationRepository.count();
        assertEquals(relationCount, 4);
    }

    @Test
    @DirtiesContext
    void addRelation_Fail_ExistingRelation() throws Exception {
        Movie movieFirst = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Movie movieSecond = Movie.builder().name(MOVIE_NAME_DEADPOOL).build();
        Director directorJane = Director.builder().name(DIRECTOR_NAME_JANE).build();
        Director directorJim = Director.builder().name(DIRECTOR_NAME_JIM).build();

        movieRepository.saveAll(List.of(movieFirst, movieSecond));
        directorRepository.saveAll(List.of(directorJane, directorJim));
        movieDirectorRelationRepository.saveAll(List.of(
                MovieDirectorRelation.builder().directorId(directorJim.getId()).movieId(movieFirst.getId()).build(),
                MovieDirectorRelation.builder().directorId(directorJim.getId()).movieId(movieSecond.getId()).build(),
                MovieDirectorRelation.builder().directorId(directorJane.getId()).movieId(movieSecond.getId()).build()
        ));
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "movieId": "%s",
                                              "directorId": "%s"
                                         }
                                        """.formatted(movieFirst.getId(), directorJim.getId())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
        Long relationCount = movieDirectorRelationRepository.count();
        assertEquals(relationCount, 3);
    }

    @Test
    @DirtiesContext
    void deleteRelation() throws Exception {
        Movie movieFirst = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Movie movieSecond = Movie.builder().name(MOVIE_NAME_DEADPOOL).build();
        Director directorJane = Director.builder().name(DIRECTOR_NAME_JANE).build();
        Director directorJim = Director.builder().name(DIRECTOR_NAME_JIM).build();

        movieRepository.saveAll(List.of(movieFirst, movieSecond));
        directorRepository.saveAll(List.of(directorJane, directorJim));
        movieDirectorRelationRepository.saveAll(List.of(
                MovieDirectorRelation.builder().directorId(directorJim.getId()).movieId(movieFirst.getId()).build(),
                MovieDirectorRelation.builder().directorId(directorJim.getId()).movieId(movieSecond.getId()).build(),
                MovieDirectorRelation.builder().directorId(directorJane.getId()).movieId(movieSecond.getId()).build()
        ));
        assertEquals(movieDirectorRelationRepository.count(), 3);
        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "movieId": "%s",
                                              "directorId": "%s"
                                         }
                                        """.formatted(movieFirst.getId(), directorJim.getId())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals(movieDirectorRelationRepository.count(), 2);
    }

    @Test
    @DirtiesContext
    void deleteRelation_Failed() throws Exception {
        Movie movieFirst = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Movie movieSecond = Movie.builder().name(MOVIE_NAME_DEADPOOL).build();
        Director directorJane = Director.builder().name(DIRECTOR_NAME_JANE).build();
        Director directorJim = Director.builder().name(DIRECTOR_NAME_JIM).build();

        movieRepository.saveAll(List.of(movieFirst, movieSecond));
        directorRepository.saveAll(List.of(directorJane, directorJim));
        movieDirectorRelationRepository.saveAll(List.of(
                MovieDirectorRelation.builder().directorId(directorJim.getId()).movieId(movieFirst.getId()).build(),
                MovieDirectorRelation.builder().directorId(directorJim.getId()).movieId(movieSecond.getId()).build(),
                MovieDirectorRelation.builder().directorId(directorJane.getId()).movieId(movieSecond.getId()).build()
        ));
        assertEquals(movieDirectorRelationRepository.count(), 3);
        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "movieId": "%s",
                                              "directorId": "%s"
                                         }
                                        """.formatted(movieFirst.getId(), directorJane.getId())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        assertEquals(movieDirectorRelationRepository.count(), 3);
    }

    @Test
    @DirtiesContext
    void getDirectorsByMovieIdTest_multipleMatch() throws Exception {
        Movie movieFirst = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Movie movieSecond = Movie.builder().name(MOVIE_NAME_DEADPOOL).build();
        Director directorJane = Director.builder().name(DIRECTOR_NAME_JANE).build();
        Director directorJim = Director.builder().name(DIRECTOR_NAME_JIM).build();
        Director directorJoe = Director.builder().name(DIRECTOR_NAME_JOE).build();
        movieRepository.saveAll(List.of(movieFirst, movieSecond));
        directorRepository.saveAll(List.of(directorJane, directorJim, directorJoe));
        movieDirectorRelationRepository.saveAll(
                List.of(
                        MovieDirectorRelation.builder().directorId(directorJane.getId()).movieId(movieFirst.getId()).build(),
                        MovieDirectorRelation.builder().directorId(directorJim.getId()).movieId(movieSecond.getId()).build(),
                        MovieDirectorRelation.builder().directorId(directorJoe.getId()).movieId(movieSecond.getId()).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_WITH_ID, movieSecond.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder(
                        directorJim.getName(),
                        directorJoe.getName()
                )));
    }

    @Test
    @DirtiesContext
    void getDirectorsByMovieIdTest_noMatch() throws Exception {
        Movie movieFirst = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Movie movieSecond = Movie.builder().name(MOVIE_NAME_DEADPOOL).build();
        Director directorJane = Director.builder().name(DIRECTOR_NAME_JANE).build();
        Director directorJim = Director.builder().name(DIRECTOR_NAME_JIM).build();
        Director directorJoe = Director.builder().name(DIRECTOR_NAME_JOE).build();
        movieRepository.saveAll(List.of(movieFirst, movieSecond));
        directorRepository.saveAll(List.of(directorJane, directorJim, directorJoe));
        movieDirectorRelationRepository.saveAll(
                List.of(
                        MovieDirectorRelation.builder().directorId(directorJane.getId()).movieId(movieFirst.getId()).build(),
                        MovieDirectorRelation.builder().directorId(directorJim.getId()).movieId(movieFirst.getId()).build(),
                        MovieDirectorRelation.builder().directorId(directorJoe.getId()).movieId(movieFirst.getId()).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_WITH_ID, movieSecond.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}