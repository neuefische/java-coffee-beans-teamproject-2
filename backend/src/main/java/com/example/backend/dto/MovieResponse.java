package com.example.backend.dto;

import com.example.backend.model.Movie;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record MovieResponse(@NonNull String id, @NonNull String name) {
    public static MovieResponse from(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .name(movie.getName())
                .build();
    }
}
