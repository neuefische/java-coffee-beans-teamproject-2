package com.example.backend.controller;

import com.example.backend.dto.ActorResponse;
import com.example.backend.dto.CreateActorRequest;
import com.example.backend.model.Actor;
import com.example.backend.service.ActorService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/actor")
@Validated
public class ActorController {
    private final ActorService actorService;

    @PostMapping
    public ActorResponse save(@RequestBody @NotNull CreateActorRequest request) {
        Actor actor = request.toActor();

        return ActorResponse.from(actorService.createActor(actor));
    }

    @GetMapping("/autocompletion/{prefix}")
    public List<ActorResponse> getByName(@PathVariable @NonNull String prefix) {
        return actorService.getActorsByPrefix(prefix).stream().map(ActorResponse::from).toList();
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
