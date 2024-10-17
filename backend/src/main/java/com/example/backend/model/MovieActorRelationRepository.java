package com.example.backend.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieActorRelationRepository extends JpaRepository<MovieActorRelation, Long> {
    List<MovieActorRelation> findByMovieId(Long movieId);
}
