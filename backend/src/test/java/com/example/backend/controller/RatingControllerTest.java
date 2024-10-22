package com.example.backend.controller;

import com.example.backend.model.Rating;
import com.example.backend.model.RatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class RatingControllerTest {
    private static final String URL_BASE = "/api/rating";
    private static final String URL_WITH_ID = "/api/rating/{id}";

    private static final String MOVIE_ID_FIRST = "1";
    private static final String MOVIE_ID_SECOND = "2";
    private static final boolean WATCHED_FIRST = true;
    private static final boolean WATCHED_SECOND = false;
    private static final int RATING_FIRST = 7;
    private static final int RATING_SECOND = 8;
    private static final String USER_FIRST = "First";
    private static final String USER_SECOND = "Second";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    @DirtiesContext
    public void saveTest_new() throws Exception {
        ratingRepository.saveAll(
                List.of(
                        Rating.builder().movieId(MOVIE_ID_FIRST).userId(USER_SECOND).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build(),
                        Rating.builder().movieId(MOVIE_ID_SECOND).userId(USER_FIRST).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build(),
                        Rating.builder().movieId(MOVIE_ID_SECOND).userId(USER_SECOND).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "movieId": "%s",
                                            "isWatched": %s,
                                            "rating": %s
                                        }
                                        """.formatted(MOVIE_ID_FIRST, WATCHED_FIRST, RATING_FIRST)
                        )
                        .with(mockUser())).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Long totalRatings = ratingRepository.count();
        assertEquals(4, totalRatings);
        Optional<Rating> result = ratingRepository.findFirstByUserIdAndMovieId(USER_FIRST, MOVIE_ID_FIRST);
        assertTrue(result.isPresent());
        assertEquals(WATCHED_FIRST, result.get().isWatched());
        assertEquals(RATING_FIRST, result.get().getRating());
    }

    @Test
    @DirtiesContext
    public void saveTest_existent() throws Exception {
        ratingRepository.saveAll(
                List.of(
                        Rating.builder().movieId(MOVIE_ID_FIRST).userId(USER_FIRST).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build(),
                        Rating.builder().movieId(MOVIE_ID_SECOND).userId(USER_FIRST).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build(),
                        Rating.builder().movieId(MOVIE_ID_SECOND).userId(USER_SECOND).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "movieId": "%s",
                                            "isWatched": %s,
                                            "rating": %s
                                        }
                                        """.formatted(MOVIE_ID_FIRST, WATCHED_FIRST, RATING_FIRST)
                        )
                        .with(mockUser())).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Long totalRatings = ratingRepository.count();
        assertEquals(3, totalRatings);
        Optional<Rating> result = ratingRepository.findFirstByUserIdAndMovieId(USER_FIRST, MOVIE_ID_FIRST);
        assertTrue(result.isPresent());
        assertEquals(WATCHED_FIRST, result.get().isWatched());
        assertEquals(RATING_FIRST, result.get().getRating());
    }

    @Test
    public void saveTest_unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "movieId": "%s",
                                            "isWatched": %s,
                                            "rating": %s
                                        }
                                        """.formatted(MOVIE_ID_FIRST, WATCHED_FIRST, RATING_FIRST)
                        ))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    void getTest_successful() throws Exception {
        ratingRepository.saveAll(
                List.of(
                        Rating.builder().movieId(MOVIE_ID_FIRST).userId(USER_FIRST).isWatched(WATCHED_FIRST).rating(RATING_FIRST).build(),
                        Rating.builder().movieId(MOVIE_ID_FIRST).userId(USER_SECOND).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build(),
                        Rating.builder().movieId(MOVIE_ID_SECOND).userId(USER_FIRST).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build(),
                        Rating.builder().movieId(MOVIE_ID_SECOND).userId(USER_SECOND).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_WITH_ID, MOVIE_ID_FIRST)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movieId").value(MOVIE_ID_FIRST))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isWatched").value(WATCHED_FIRST))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(RATING_FIRST))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(USER_FIRST));
    }

    @Test
    @DirtiesContext
    void getTest_failed() throws Exception {
        ratingRepository.saveAll(
                List.of(
                        Rating.builder().movieId(MOVIE_ID_FIRST).userId(USER_SECOND).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build(),
                        Rating.builder().movieId(MOVIE_ID_SECOND).userId(USER_FIRST).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build(),
                        Rating.builder().movieId(MOVIE_ID_SECOND).userId(USER_SECOND).isWatched(WATCHED_SECOND).rating(RATING_SECOND).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_WITH_ID, MOVIE_ID_FIRST)
                .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void getTest_unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_WITH_ID, MOVIE_ID_FIRST))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    private OidcLoginRequestPostProcessor mockUser() {
        return oidcLogin().userInfoToken(token -> token
                .claim("login", USER_FIRST));
    }
}