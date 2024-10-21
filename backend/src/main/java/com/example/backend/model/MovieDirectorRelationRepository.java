package com.example.backend.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieDirectorRelationRepository extends MongoRepository<MovieDirectorRelation, String> {
    List<MovieDirectorRelation> findByMovieId(String movieId);

    Optional<MovieDirectorRelation> findByMovieIdAndDirectorId(String movieId, String directorId);

    void deleteByMovieId(String movieId);
}

