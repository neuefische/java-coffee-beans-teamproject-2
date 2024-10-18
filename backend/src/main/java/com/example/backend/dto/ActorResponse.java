package com.example.backend.dto;

import com.example.backend.model.Actor;
import lombok.Builder;

@Builder
public record ActorResponse(String id, String name) {
    public static ActorResponse from(Actor actor) {
        return ActorResponse.builder()
                .id(actor.getId())
                .name(actor.getName())
                .build();
    }
}
