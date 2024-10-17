package com.example.backend.dto;

import com.example.backend.model.Director;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record CreateDirectorRequest(
        @NonNull @NotBlank(message = "Director name cannot be empty") String name) {
    public Director toDirector() {
        return Director.builder().name(name).build();
    }
}
