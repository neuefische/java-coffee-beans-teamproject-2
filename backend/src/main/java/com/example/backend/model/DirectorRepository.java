package com.example.backend.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends MongoRepository<Director, String> {
    List<Director> findByNameStartingWith(String prefix);
}
