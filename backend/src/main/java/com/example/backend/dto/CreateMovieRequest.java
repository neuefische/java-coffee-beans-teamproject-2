package com.example.backend.dto;

import com.example.backend.model.Actor;
import com.example.backend.model.Movie;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.NonNull;


public record CreateMovieRequest(
        @NonNull String name,
        boolean isWatched,
        @Min(Movie.MIN_RATING) @Max(Movie.MAX_RATING) Integer rating

) {
    public Movie toMovie() {
        return Movie.builder().name(name).isWatched(isWatched).rating(isWatched ? rating : null).build();
    }
}
