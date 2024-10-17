package com.example.backend.dto;

import com.example.backend.model.Movie;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record MovieResponse(@NonNull Long id, @NonNull String name, @NonNull boolean isWatched,
                            @Nullable Integer rating) {
    public static MovieResponse from(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .name(movie.getName())
                .isWatched(movie.isWatched())
                .rating(movie.getRating())
                .build();
    }
}
