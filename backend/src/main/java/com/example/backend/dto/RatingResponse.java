package com.example.backend.dto;

import com.example.backend.model.Rating;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record RatingResponse(String userId, String movieId, Integer rating,
                             boolean isWatched) {
    public static RatingResponse from(Rating rating) {
        return RatingResponse.builder()
                .userId(rating.getUserId())
                .movieId(rating.getMovieId())
                .rating(rating.getRating())
                .isWatched(rating.isWatched())
                .build();
    }
}
