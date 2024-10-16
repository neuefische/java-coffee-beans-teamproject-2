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

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {
    private static final String URL_BASE = "/api/movie";

    private static final long ID_FIRST = 1L;

    private static final String NAME_MEMENTO = "Memento";
    private static final String NAME_DEADPOOL = "Deadpool";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DirtiesContext
    void saveTest_correct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
{
    "name": "%s",
    "isWatched": true
}
""".formatted(NAME_MEMENTO)
                ))
                .andExpect(MockMvcResultMatchers.status().isOk());
        List<Movie> actualMovies = movieRepository.findAll();
        List<Movie> expectedMovies = List.of(Movie.builder().id(ID_FIRST).name(NAME_MEMENTO).isWatched(true).build());
        assertEquals(expectedMovies, actualMovies);
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
                        ))
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
                        Movie.builder().name(NAME_MEMENTO).build(),
                        Movie.builder().name(NAME_DEADPOOL).build()
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(NAME_MEMENTO))
                .andExpect(jsonPath("$[1].name").value(NAME_DEADPOOL));

    }
}