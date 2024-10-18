package com.example.backend.dto;

import com.example.backend.model.Actor;
import com.example.backend.model.Director;
import lombok.Builder;

@Builder
public record DirectorResponse(Long id, String name) {
    public static DirectorResponse from(Director director) {
        return DirectorResponse.builder()
                .id(director.getId())
                .name(director.getName())
                .build();
    }
}
