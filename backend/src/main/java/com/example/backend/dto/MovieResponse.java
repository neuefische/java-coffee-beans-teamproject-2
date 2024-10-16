package com.example.backend.dto;

import com.example.backend.model.Movie;
import lombok.Builder;

@Builder
public record MovieResponse(Long id, String name) {
    public static MovieResponse from(Movie movie) {
        return MovieResponse.builder().id(movie.getId()).name(movie.getName()).build();
    }
}
