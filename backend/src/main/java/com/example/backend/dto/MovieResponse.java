package com.example.backend.dto;

import com.example.backend.model.Movie;
import lombok.Builder;

@Builder
public record MovieResponse(Long id, String name, boolean isWatched, int rating) {
    public static MovieResponse from(Movie movie) {
        return MovieResponse.builder().id(movie.getId()).name(movie.getName()).isWatched(movie.isWatched()).rating((movie.getRating() != null)? movie.getRating() : 0).build();
    }
}
