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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ActorMovieControllerTest {
    private static final String URL_ACTOR_MOVIE_BASE = "/api/actor-movie/{movieId}";

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
    private ActorMovieRelationRepository actorMovieRelationRepository;

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
        actorMovieRelationRepository.saveAll(
                List.of(
                        ActorMovieRelation.builder().actor(actorJane).movie(movieFirst).build(),
                        ActorMovieRelation.builder().actor(actorJoe).movie(movieSecond).build(),
                        ActorMovieRelation.builder().actor(actorJim).movie(movieSecond).build(),
                        ActorMovieRelation.builder().actor(actorJane).movie(movieSecond).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_ACTOR_MOVIE_BASE, movieSecond.getId()))
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
        actorMovieRelationRepository.saveAll(
                List.of(
                        ActorMovieRelation.builder().actor(actorJane).movie(movieFirst).build(),
                        ActorMovieRelation.builder().actor(actorJoe).movie(movieFirst).build(),
                        ActorMovieRelation.builder().actor(actorJim).movie(movieFirst).build(),
                        ActorMovieRelation.builder().actor(actorJohn).movie(movieFirst).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_ACTOR_MOVIE_BASE, movieSecond.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getByMovieIdTest_emptyRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_ACTOR_MOVIE_BASE, ""))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DirtiesContext
    void addActorById_successFull() throws Exception {
        Movie movie = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Actor actor = Actor.builder().name(ACTOR_NAME_JANE).build();
        movieRepository.save(movie);
        actorRepository.save(actor);

        mockMvc.perform(MockMvcRequestBuilders.post(URL_ACTOR_MOVIE_BASE, movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "id": "%s"
                                         }
                                        """.formatted(actor.getId())
                        )
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<ActorMovieRelation> actualActorIdList = actorMovieRelationRepository.findByMovieId(movie.getId());
        assertEquals(1, actualActorIdList.size());
        assertEquals(actor.getId(), actualActorIdList.getFirst().getActor().getId());
    }

    @Test
    @DirtiesContext
    void addActorById_alreadyAdded() throws Exception {
        Movie movie = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Actor actor = Actor.builder().name(ACTOR_NAME_JANE).build();
        movieRepository.save(movie);
        actorRepository.save(actor);
        actorMovieRelationRepository.save(ActorMovieRelation.builder().actor(actor).movie(movie).build());

        mockMvc.perform(MockMvcRequestBuilders.post(URL_ACTOR_MOVIE_BASE, movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "id": "%s"
                                         }
                                        """.formatted(actor.getId())
                        ))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    @DirtiesContext
    void addActorById_noSuchActor() throws Exception {
        Movie movie = Movie.builder().name(MOVIE_NAME_MEMENTO).build();
        Actor actor = Actor.builder().name(ACTOR_NAME_JANE).build();
        movieRepository.save(movie);
        actorRepository.save(actor);
        Long nonExistentActorId = actor.getId() + 1;

        mockMvc.perform(MockMvcRequestBuilders.post(URL_ACTOR_MOVIE_BASE, movie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "id": "%s"
                                         }
                                        """.formatted(nonExistentActorId)
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
        Long nonExistentMovieId = movie.getId() + 1L;

        mockMvc.perform(MockMvcRequestBuilders.post(URL_ACTOR_MOVIE_BASE, nonExistentMovieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                         {
                                              "id": "%s"
                                         }
                                        """.formatted(actor.getId())
                        ))

                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}