package com.example.backend.model;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieActorRelationRepository extends MongoRepository<MovieActorRelation, String> {
    List<MovieActorRelation> findByMovieId(String movieId);

    Optional<MovieActorRelation> findByMovieIdAndActorId(String movieId, String actorId);

    void deleteByMovieId(String movieId);
}
