package com.example.backend.dto;

import com.example.backend.model.Actor;
import lombok.NonNull;

public record CreateActorRequest(
        @NonNull String name
        ) {
    public Actor toActor() {
        return Actor.builder().name(name).build();
    }
}
