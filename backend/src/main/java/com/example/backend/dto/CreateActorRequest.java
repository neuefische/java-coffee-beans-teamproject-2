package com.example.backend.dto;

import com.example.backend.model.Actor;

public record CreateActorRequest(
        String name
) {
    public Actor toActor() {
        return Actor.builder().name(name).build();
    }
}
