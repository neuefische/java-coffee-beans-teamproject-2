package com.example.backend.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieActorRelationRepository extends JpaRepository<MovieActorRelation, Long> {
    List<MovieActorRelation> findByMovieId(Long movieId);
    Optional<MovieActorRelation> findByMovieIdAndActorId(Long movieId, Long actorId);
}
