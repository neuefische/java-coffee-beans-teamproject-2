package com.example.backend.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findAllByIsWatchedIsFalse();

    List<Movie> findAllByIsWatchedIsTrue();
}
