package com.example.backend.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {
    Optional<Rating> findFirstByUserIdAndMovieId(String userId, String movieId);

    List<Rating> findAllByUserIdAndIsWatchedIsFalse(String userId);

    List<Rating> findAllByUserIdAndIsWatchedIsTrue(String userId);

    List<Rating> findAllByMovieIdAndUserId(List<String> movieId, String userId);
}
