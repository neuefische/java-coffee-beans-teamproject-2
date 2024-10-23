package com.example.backend.dto;

import com.example.backend.model.Movie;
import com.example.backend.model.Rating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.NonNull;


public record CreateRatingRequest(
        @NonNull String movieId,
        boolean isWatched,
        @Min(Rating.MIN_RATING) @Max(Rating.MAX_RATING) Integer rating
) {
    public Rating toRating(String userId) {
        return Rating.builder().userId(userId).movieId(movieId).isWatched(isWatched).rating(rating).build();
    }
}
