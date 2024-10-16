package com.example.backend.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorMovieRelationRepository extends JpaRepository<ActorMovieRelation, Long> {
    List<ActorMovieRelation> findByMovieId(Long movieId);
}
