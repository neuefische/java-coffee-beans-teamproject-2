package com.example.backend.dto;

import com.example.backend.model.Movie;
import lombok.NonNull;

public record CreateMovieRequest(
        @NonNull String name
) {
    public Movie toMovie() {
        return Movie.builder().name(name).build();
    }
}
