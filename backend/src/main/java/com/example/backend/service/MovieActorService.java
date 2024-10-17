package com.example.backend.service;

import com.example.backend.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieActorService {
    private final MovieActorRelationRepository movieActorRelationRepository;

    private final ActorRepository actorRepository;

    private final MovieRepository movieRepository;

    public List<Actor> getActorsByMovieId(Long movieId) {
        return movieActorRelationRepository.findByMovieId(movieId)
                .stream()
                .map(MovieActorRelation::getActor)
                .toList();
    }

    public void addActor(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Actor actor = actorRepository.findById(actorId).orElseThrow();
        movieActorRelationRepository.save(MovieActorRelation.builder().actor(actor).movie(movie).actor(actor).build());
    }

    public void removeActor(Long movieId, Long actorId) {
        MovieActorRelation relation = movieActorRelationRepository
                .findByMovieIdAndActorId(actorId, movieId)
                .orElseThrow();

        movieActorRelationRepository.delete(relation);
    }
}
