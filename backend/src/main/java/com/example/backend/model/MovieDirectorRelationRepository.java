package com.example.backend.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieDirectorRelationRepository extends JpaRepository<MovieDirectorRelation, Long> {
    List<MovieDirectorRelation> findByMovieId(Long movieId);
    Optional<MovieDirectorRelation> findByMovieIdAndDirectorId(Long movieId, Long directorId);
}

