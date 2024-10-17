package com.example.backend.dto;

import com.example.backend.model.Actor;
import com.example.backend.model.Director;
import lombok.NonNull;

public record CreateDirectorRequest(
        @NonNull String name
) {
    public Director toDirector() {
        return Director.builder().name(name).build();
    }
}
