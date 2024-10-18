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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class MovieActorControllerTest {
    private static final String URL_BASE = "/api/movie-actor";
    private static final String URL_WITH_ID = "/api/movie-actor/{movieId}";

    private static final String ACTOR_NAME_JANE = "Jane Doe";
    private static final String ACTOR_NAME_JIM = "Jim Doe";
    private static final String ACTOR_NAME_JOE = "Joe Doe";
    private static final String ACTOR_NAME_JOHN = "John Doe";

    private static final String MOVIE_NAME_MEMENTO = "Memento";
    private static final String MOVIE_NAME_DEADPOOL = "Deadpool";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MovieActorRelationRepository movieActorRelationRepository;

    @Test
    @DirtiesContext
    void getByMovieIdTest_multipleMatch() throws Exception {
        Movie movieFirst = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Movie movieSecond = Movie.builder().name(MOVIE_NAME_DEADPOOL).build();
        Actor actorJane = Actor.builder().name(ACTOR_NAME_JANE).build();
        Actor actorJim = Actor.builder().name(ACTOR_NAME_JIM).build();
        Actor actorJoe = Actor.builder().name(ACTOR_NAME_JOE).build();
        Actor actorJohn = Actor.builder().name(ACTOR_NAME_JOHN).build();
        movieRepository.saveAll(List.of(movieFirst, movieSecond));
        actorRepository.saveAll(List.of(actorJane, actorJim, actorJoe, actorJohn));
        movieActorRelationRepository.saveAll(
                List.of(
                        MovieActorRelation.builder().actorId(actorJane.getId()).movieId(movieFirst.getId()).build(),
                        MovieActorRelation.builder().actorId(actorJoe.getId()).movieId(movieSecond.getId()).build(),
                        MovieActorRelation.builder().actorId(actorJim.getId()).movieId(movieSecond.getId()).build(),
                        MovieActorRelation.builder().actorId(actorJane.getId()).movieId(movieSecond.getId()).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_WITH_ID, movieSecond.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder(
                        actorJoe.getName(),
                        actorJim.getName(),
                        actorJane.getName()
                )));
    }

    @Test
    @DirtiesContext
    void getByMovieIdTest_noMatch() throws Exception {
        Movie movieFirst = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Movie movieSecond = Movie.builder().name(MOVIE_NAME_DEADPOOL).build();
        Actor actorJane = Actor.builder().name(ACTOR_NAME_JANE).build();
        Actor actorJim = Actor.builder().name(ACTOR_NAME_JIM).build();
        Actor actorJoe = Actor.builder().name(ACTOR_NAME_JOE).build();
        Actor actorJohn = Actor.builder().name(ACTOR_NAME_JOHN).build();
        movieRepository.saveAll(List.of(movieFirst, movieSecond));
        actorRepository.saveAll(List.of(actorJane, actorJim, actorJoe, actorJohn));
        movieActorRelationRepository.saveAll(
                List.of(
                        MovieActorRelation.builder().actorId(actorJane.getId()).movieId(movieFirst.getId()).build(),
                        MovieActorRelation.builder().actorId(actorJoe.getId()).movieId(movieFirst.getId()).build(),
                        MovieActorRelation.builder().actorId(actorJim.getId()).movieId(movieFirst.getId()).build(),
                        MovieActorRelation.builder().actorId(actorJohn.getId()).movieId(movieFirst.getId()).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_WITH_ID, movieSecond.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getByMovieIdTest_emptyRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_WITH_ID, ""))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DirtiesContext
    void addActorById_successFull() throws Exception {
        Movie movie = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Actor actor = Actor.builder().name(ACTOR_NAME_JANE).build();
        movieRepository.save(movie);
        actorRepository.save(actor);

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "actorId": "%s",
                                              "movieId": "%s"
                                         }
                                        """.formatted(actor.getId(), movie.getId())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<MovieActorRelation> actualActorIdList = movieActorRelationRepository.findByMovieId(movie.getId());
        assertEquals(1, actualActorIdList.size());
        assertEquals(actor.getId(), actualActorIdList.getFirst().getActorId());
    }

    @Test
    @DirtiesContext
    void addActorById_alreadyAdded() throws Exception {
        Movie movie = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Actor actor = Actor.builder().name(ACTOR_NAME_JANE).build();
        movieRepository.save(movie);
        actorRepository.save(actor);
        movieActorRelationRepository.save(MovieActorRelation.builder().actorId(actor.getId()).movieId(movie.getId()).build());

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "actorId": "%s",
                                              "movieId": "%s"
                                         }
                                        """.formatted(actor.getId(), movie.getId())
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DirtiesContext
    void addActorById_noSuchActor() throws Exception {
        Movie movie = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Actor actor = Actor.builder().name(ACTOR_NAME_JANE).build();
        movieRepository.save(movie);
        actorRepository.save(actor);
        String nonExistentActorId = actor.getId() + "1";

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "actorId": "%s",
                                              "movieId": "%s",
                                         }
                                        """.formatted(nonExistentActorId, movie.getId())
                        ))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DirtiesContext
    void addActorById_noSuchMovie() throws Exception {
        Movie movie = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Actor actor = Actor.builder().name(ACTOR_NAME_JANE).build();
        movieRepository.save(movie);
        actorRepository.save(actor);
        String nonExistentMovieId = movie.getId() + "1L";

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "actorId": "%s",
                                              "movieId": "%s"
                                         }
                                        """.formatted(actor.getId(), nonExistentMovieId)
                        ))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DirtiesContext
    void removeActor_successful() throws Exception {
        Movie movieFirst = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Movie movieSecond = Movie.builder().name(MOVIE_NAME_DEADPOOL).build();
        Actor actorJane = Actor.builder().name(ACTOR_NAME_JANE).build();
        Actor actorJim = Actor.builder().name(ACTOR_NAME_JIM).build();
        movieRepository.save(movieFirst);
        movieRepository.save(movieSecond);
        actorRepository.save(actorJane);
        actorRepository.save(actorJim);
        movieActorRelationRepository.save(MovieActorRelation.builder().actorId(actorJane.getId()).movieId(movieFirst.getId()).build());
        movieActorRelationRepository.save(MovieActorRelation.builder().actorId(actorJim.getId()).movieId(movieFirst.getId()).build());
        movieActorRelationRepository.save(MovieActorRelation.builder().actorId(actorJane.getId()).movieId(movieSecond.getId()).build());
        movieActorRelationRepository.save(MovieActorRelation.builder().actorId(actorJim.getId()).movieId(movieSecond.getId()).build());

        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "actorId": "%s",
                                              "movieId": "%s"
                                         }
                                        """.formatted(actorJane.getId(), movieFirst.getId())
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertTrue(
                movieActorRelationRepository.findByMovieIdAndActorId(movieFirst.getId(), actorJane.getId()).isEmpty()
        );
        assertEquals(
                3,
                movieActorRelationRepository.findAll().size()
        );
    }

    @Test
    @DirtiesContext
    void removeActor_noSuchRelation() throws Exception {
        Movie movieFirst = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Movie movieSecond = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Actor actorJane = Actor.builder().name(ACTOR_NAME_JANE).build();
        Actor actorJim = Actor.builder().name(ACTOR_NAME_JANE).build();
        movieRepository.save(movieFirst);
        movieRepository.save(movieSecond);
        actorRepository.save(actorJane);
        actorRepository.save(actorJim);
        movieActorRelationRepository.save(MovieActorRelation.builder().actorId(actorJim.getId()).movieId(movieFirst.getId()).build());
        movieActorRelationRepository.save(MovieActorRelation.builder().actorId(actorJane.getId()).movieId(movieSecond.getId()).build());
        movieActorRelationRepository.save(MovieActorRelation.builder().actorId(actorJim.getId()).movieId(movieSecond.getId()).build());

        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "actorId": "%s",
                                              "movieId": "%s"
                                         }
                                        """.formatted(actorJane.getId(), movieFirst.getId())
                        ))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}