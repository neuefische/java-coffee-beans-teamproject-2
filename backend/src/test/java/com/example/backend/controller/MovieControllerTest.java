package com.example.backend.controller;

import com.example.backend.model.Movie;
import com.example.backend.model.MovieRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {
    private static final String URL_BASE = "/api/movie";
    private static final String URL_WATCHED = "/api/movie/watched";
    private static final String URL_WISHLIST = "/api/movie/wishlist";

    private static final String NAME_FIRST = "Memento";
    private static final String NAME_SECOND = "Deadpool";
    private static final String NAME_THIRD = "Third";

    private static final int RATING_ONE = 1;
    private static final int RATING_TWO = 2;

    private static final String USER_FIRST = "First";
    private static final String USER_SECOND = "Second";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    @DirtiesContext
    void saveTest_correct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "name": "%s"
                                        }
                                        """.formatted(NAME_FIRST)
                        )
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        List<Movie> actualMovies = movieRepository.findAll();
        assertEquals(1, actualMovies.size());
        assertEquals(NAME_FIRST, actualMovies.getFirst().getName());
    }

    @Test
    @DirtiesContext
    void saveTest_Unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "name": "%s",
                                            "isWatched": true
                                        }
                                        """.formatted(NAME_FIRST)
                        ))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    void saveTest_Name_Null() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "name": null
                                        }
                                        """
                        )
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        List<Movie> actualMovies = movieRepository.findAll();
        List<Movie> expectedMovies = List.of();
        assertEquals(expectedMovies, actualMovies);
    }

    @Test
    @DirtiesContext
    void getAll() throws Exception {
        movieRepository.saveAll(
                List.of(
                        Movie.builder().name(NAME_FIRST).build(),
                        Movie.builder().name(NAME_SECOND).build()
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(NAME_FIRST))
                .andExpect(jsonPath("$[1].name").value(NAME_SECOND));

    }

    @Test
    @DirtiesContext
    void getAll_Unauthorized() throws Exception {
        movieRepository.saveAll(
                List.of(
                        Movie.builder().name(NAME_FIRST).build(),
                        Movie.builder().name(NAME_SECOND).build()
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    void getMovie_By_ID_Test() throws Exception {
        Movie firstMovie = Movie.builder().name(NAME_FIRST).build();
        Movie secondMovie = Movie.builder().name(NAME_SECOND).build();

        movieRepository.saveAll(List.of(firstMovie, secondMovie));
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" + firstMovie.getId())
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(NAME_FIRST))
                .andExpect(jsonPath("$.id").value(firstMovie.getId()));

    }

    @Test
    @DirtiesContext
    void getMovie_By_NonExistingID() throws Exception {
        movieRepository.saveAll(
                List.of(
                        Movie.builder().name(NAME_FIRST).build(),
                        Movie.builder().name(NAME_SECOND).build()
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" + 3)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    @DirtiesContext
    void updateMovie_Sucessfull() throws Exception {
        Movie firstMovie = Movie.builder().name(NAME_FIRST).build();
        Movie secondMovie = Movie.builder().name(NAME_SECOND).build();
        movieRepository.saveAll(List.of(firstMovie, secondMovie));

        mockMvc.perform(MockMvcRequestBuilders.put(URL_BASE + "/" + firstMovie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                                "name": "Memento Edited",
                                                "isWatched": true,
                                                "rating": 10
                                        }
                                        """
                        )
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Memento Edited"))
                .andExpect(jsonPath("$[1].name").value(NAME_SECOND));
    }

    @Test
    @DirtiesContext
    void updateMovie_Unauthorized() throws Exception {
        Movie firstMovie = Movie.builder().name(NAME_FIRST).build();
        Movie secondMovie = Movie.builder().name(NAME_SECOND).build();
        movieRepository.saveAll(List.of(firstMovie, secondMovie));

        mockMvc.perform(MockMvcRequestBuilders.put(URL_BASE + "/" + firstMovie.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                                "name": "Memento Edited",
                                                "isWatched": true,
                                                "rating": 10
                                        }
                                        """
                        ))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    void updateMovie_NonExistand_ID() throws Exception {
        movieRepository.saveAll(
                List.of(
                        Movie.builder().name(NAME_FIRST).build(),
                        Movie.builder().name(NAME_SECOND).build()
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.put(URL_BASE + "/" + 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                                "name": "Memento Edited",
                                                "isWatched": true,
                                                "rating": 10
                                        }
                                        """
                        )
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(NAME_FIRST))
                .andExpect(jsonPath("$[1].name").value(NAME_SECOND));
    }

    @Test
    @DirtiesContext
    void deleteTest_Successful() throws Exception {
        Movie firstMovie = Movie.builder().name(NAME_FIRST).build();
        Movie secondMovie = Movie.builder().name(NAME_SECOND).build();

        movieRepository.saveAll(
                List.of(
                        firstMovie,
                        secondMovie
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE + "/" + firstMovie.getId())
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(NAME_SECOND));
    }

    @Test
    @DirtiesContext
    void deleteTest_Unauthorized() throws Exception {
        Movie firstMovie = Movie.builder().name(NAME_FIRST).build();
        Movie secondMovie = Movie.builder().name(NAME_SECOND).build();

        movieRepository.saveAll(
                List.of(
                        firstMovie,
                        secondMovie
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE + "/" + firstMovie.getId()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    void deleteTest_NonExisting_ID() throws Exception {
        movieRepository.saveAll(
                List.of(
                        Movie.builder().name(NAME_FIRST).build(),
                        Movie.builder().name(NAME_SECOND).build()
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE + "/" + 3)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DirtiesContext
    void getWatchedMoviesTest_successful() throws Exception {
        Movie movieWatchedFirst = Movie.builder().name(NAME_FIRST).build();
        Movie movieWatchedSecond = Movie.builder().name(NAME_SECOND).build();
        Movie movieWishlisted = Movie.builder().name(NAME_THIRD).build();

        movieRepository.saveAll(
                List.of(
                        movieWatchedFirst,
                        movieWatchedSecond,
                        movieWishlisted
                )
        );

        ratingRepository.saveAll(
                List.of(
                        Rating.builder().movieId(movieWatchedFirst.getId()).userId(USER_FIRST).isWatched(true).rating(RATING_ONE).build(),
                        Rating.builder().movieId(movieWatchedSecond.getId()).userId(USER_FIRST).isWatched(true).rating(RATING_TWO).build(),
                        Rating.builder().movieId(movieWishlisted.getId()).userId(USER_FIRST).isWatched(false).rating(RATING_ONE).build(),
                        Rating.builder().movieId(movieWishlisted.getId()).userId(USER_SECOND).isWatched(true).rating(RATING_TWO).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_WATCHED)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieName").value(NAME_FIRST))
                .andExpect(jsonPath("$[1].movieName").value(NAME_SECOND))
                .andExpect(jsonPath("$[0].isWatched").value(true))
                .andExpect(jsonPath("$[1].isWatched").value(true))
                .andExpect(jsonPath("$[0].rating").value(RATING_ONE))
                .andExpect(jsonPath("$[1].rating").value(RATING_TWO));
    }

    @Test
    @DirtiesContext
    void getWatchedMoviesTest_Unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_WATCHED))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    void getWatchedMoviesTest_empty() throws Exception {
        Movie movieWishlistedFirst = Movie.builder().name(NAME_FIRST).build();
        Movie movieWishlistedSecond = Movie.builder().name(NAME_SECOND).build();
        Movie movieWishlistedThird = Movie.builder().name(NAME_THIRD).build();

        movieRepository.saveAll(
                List.of(
                        movieWishlistedFirst,
                        movieWishlistedSecond,
                        movieWishlistedThird
                )
        );

        ratingRepository.saveAll(
                List.of(
                        Rating.builder().movieId(movieWishlistedFirst.getId()).userId(USER_FIRST).isWatched(false).rating(RATING_ONE).build(),
                        Rating.builder().movieId(movieWishlistedSecond.getId()).userId(USER_FIRST).isWatched(false).rating(RATING_TWO).build(),
                        Rating.builder().movieId(movieWishlistedThird.getId()).userId(USER_FIRST).isWatched(false).rating(RATING_ONE).build(),
                        Rating.builder().movieId(movieWishlistedThird.getId()).userId(USER_SECOND).isWatched(true).rating(RATING_TWO).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_WATCHED)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DirtiesContext
    void getWishlistedMoviesTest_successful() throws Exception {
        Movie movieWishlistedFirst = Movie.builder().name(NAME_FIRST).build();
        Movie movieWishlistedSecond = Movie.builder().name(NAME_SECOND).build();
        Movie movieWatchedFirst = Movie.builder().name(NAME_THIRD).build();

        movieRepository.saveAll(
                List.of(
                        movieWishlistedFirst,
                        movieWishlistedSecond,
                        movieWatchedFirst
                )
        );

        ratingRepository.saveAll(
                List.of(
                        Rating.builder().movieId(movieWishlistedFirst.getId()).userId(USER_FIRST).isWatched(false).rating(RATING_ONE).build(),
                        Rating.builder().movieId(movieWishlistedSecond.getId()).userId(USER_FIRST).isWatched(false).rating(RATING_TWO).build(),
                        Rating.builder().movieId(movieWatchedFirst.getId()).userId(USER_FIRST).isWatched(true).rating(RATING_ONE).build(),
                        Rating.builder().movieId(movieWatchedFirst.getId()).userId(USER_SECOND).isWatched(false).rating(RATING_TWO).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_WISHLIST)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieName").value(NAME_FIRST))
                .andExpect(jsonPath("$[1].movieName").value(NAME_SECOND))
                .andExpect(jsonPath("$[0].isWatched").value(false))
                .andExpect(jsonPath("$[1].isWatched").value(false))
                .andExpect(jsonPath("$[0].rating").value(RATING_ONE))
                .andExpect(jsonPath("$[1].rating").value(RATING_TWO));
    }

    @Test
    @DirtiesContext
    void getWishlistedMoviesTest_Unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_WISHLIST))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    void getWishlistedMoviesTest_empty() throws Exception {
        Movie movieWatchedFirst = Movie.builder().name(NAME_FIRST).build();
        Movie movieWatchedSecond = Movie.builder().name(NAME_SECOND).build();
        Movie movieWatchedThird = Movie.builder().name(NAME_THIRD).build();

        movieRepository.saveAll(
                List.of(
                        movieWatchedFirst,
                        movieWatchedSecond,
                        movieWatchedThird
                )
        );

        ratingRepository.saveAll(
                List.of(
                        Rating.builder().movieId(movieWatchedFirst.getId()).userId(USER_FIRST).isWatched(true).rating(RATING_ONE).build(),
                        Rating.builder().movieId(movieWatchedSecond.getId()).userId(USER_FIRST).isWatched(true).rating(RATING_TWO).build(),
                        Rating.builder().movieId(movieWatchedThird.getId()).userId(USER_FIRST).isWatched(true).rating(RATING_ONE).build(),
                        Rating.builder().movieId(movieWatchedThird.getId()).userId(USER_SECOND).isWatched(false).rating(RATING_TWO).build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL_WISHLIST)
                        .with(mockUser()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private OidcLoginRequestPostProcessor mockUser() {
        return oidcLogin().userInfoToken(token -> token
                .claim("login", USER_FIRST));
    }
}
