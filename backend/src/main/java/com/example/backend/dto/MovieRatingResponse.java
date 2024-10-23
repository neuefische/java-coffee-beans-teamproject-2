package com.example.backend.dto;

import com.example.backend.model.Movie;
import com.example.backend.model.Rating;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record MovieRatingResponse(@NonNull String movieId, @NonNull String movieName, Integer rating, boolean isWatched) {
    public static MovieRatingResponse from(Rating rating, Movie movie) {
        return MovieRatingResponse.builder()
                .movieId(movie.getId())
                .movieName(movie.getName())
                .rating(rating.getRating())
                .isWatched(rating.isWatched())
                .build();
    }
}
