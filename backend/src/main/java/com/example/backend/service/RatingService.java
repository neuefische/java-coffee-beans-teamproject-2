package com.example.backend.service;

import com.example.backend.model.Rating;
import com.example.backend.model.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public void save(Rating rating) {
        ratingRepository
                .findFirstByUserIdAndMovieId(rating.getUserId(), rating.getMovieId())
                .ifPresentOrElse(
                        (entity) -> ratingRepository.save(entity.withRating(rating.getRating()).withWatched(rating.isWatched())),
                        () -> ratingRepository.save(rating)
                );
    }

    public Rating get(String userId, String movieId) {
        return ratingRepository.findFirstByUserIdAndMovieId(userId, movieId).orElseThrow();
    }
}
