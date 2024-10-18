package com.example.backend.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends MongoRepository<Actor, Long> {
}
