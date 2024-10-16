package com.example.backend.controller;

import com.example.backend.model.Actor;
import com.example.backend.model.ActorRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ActorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ActorRepository repository;

    @Test
    @DirtiesContext
    void saveTest_correct() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/actor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                 {
                                      "name": "John Doe"
                                 }
                                """
                ))
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<Actor> actual = repository.findAll();
        List<Actor> expected = List.of(Actor.builder().id(1L).movies(Set.of()).name("John Doe").build());
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void saveTest_correct_idIgnored() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/actor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                 {
                                        "id": "2",
                                        "name": "Jane Doe"
                                 }
                        """
                ))
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<Actor> actual = repository.findAll();
        List<Actor> expected = List.of(Actor.builder().id(1L).movies(Set.of()).name("Jane Doe").build());
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void saveTest_incorrectNoName() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/actor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                 {
                                        "id": "2"
                                 }
                        """
                ))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        List<Actor> actual = repository.findAll();
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
}