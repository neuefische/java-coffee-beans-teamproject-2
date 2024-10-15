package com.example.backend.controller;

import com.example.backend.dto.ActorResponse;
import com.example.backend.dto.CreateActorRequest;
import com.example.backend.model.Actor;
import com.example.backend.service.ActorService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/actor")
public class ActorController {
    private final ActorService actorService;

    @PostMapping
    public ActorResponse save(@RequestBody @NotNull CreateActorRequest request) {
        Actor actor = request.toActor();
        actorService.createActor(actor);

        return ActorResponse.from(actor);
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
    public List<ActorResponse> getAll() {
        return actorService.getAllActors().stream().map(ActorResponse::from).toList();
    }

    @DeleteMapping("/{id}")
    public void delete() {
        // TODO
    }
}
