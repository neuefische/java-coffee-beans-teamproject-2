package com.example.backend.dto;

import com.example.backend.model.Movie;
import com.example.backend.model.Rating;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record MovieRatingResponse(@NonNull String id, @NonNull String movieName, Integer rating, boolean isWatched) {
    public static MovieRatingResponse from(Movie movie, Rating rating) {
        return MovieRatingResponse.builder()
                .id(movie.getId())
                .movieName(movie.getName())
                .rating(rating.getRating())
                .isWatched(rating.isWatched())
                .build();
    }
}
