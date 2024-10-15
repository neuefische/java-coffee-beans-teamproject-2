package com.example.backend.controller;

import com.example.backend.model.Actor;
import com.example.backend.model.ActorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

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
    void save() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/actor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                 {
                                      "name": "John Doe"
                                 }
                                """
                ));

        List<Actor> actual = repository.findAll();
        List<Actor> expected = List.of(Actor.builder().id(1L).name("John Doe").build());
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