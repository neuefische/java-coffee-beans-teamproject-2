package com.example.backend.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends MongoRepository<Actor, String> {
    List<Actor> findByNameStartingWith(String prefix);
}
