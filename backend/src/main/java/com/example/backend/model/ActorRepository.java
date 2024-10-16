package com.example.backend.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findByNameStartingWith(String prefix);

    @Query("SELECT a FROM Actor a JOIN a.movies m WHERE m.id = :movieId")
    List<Actor> findByMovieId(@Param("movieId") Long movieId);
}
