package com.example.backend.controller;

import com.example.backend.model.ActorRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/actor")
public class ActorController {
    private final ActorRepository actorRepository;

    @PostMapping
    public void save() {
        // TODO
    }

    @GetMapping("/{id}")
    public void get() {
        // TODO
    }

    @PutMapping
    public void update() {
        // TODO
    }

    @GetMapping
    public void getAll() {
        // TODO
    }

    @DeleteMapping("/{id}")
    public void delete() {
        // TODO
    }
}
