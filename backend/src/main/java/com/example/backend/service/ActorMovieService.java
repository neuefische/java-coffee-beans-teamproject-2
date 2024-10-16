package com.example.backend.service;

import com.example.backend.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorMovieService {
    private final ActorMovieRelationRepository actorMovieRelationRepository;

    private final ActorRepository actorRepository;

    private final MovieRepository movieRepository;

    public List<Actor> getActorsByMovieId(Long movieId) {
        return actorMovieRelationRepository.findByMovieId(movieId)
                .stream()
                .map(ActorMovieRelation::getActor)
                .toList();
    }

    public void addActor(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Actor actor = actorRepository.findById(actorId).orElseThrow();
        actorMovieRelationRepository.save(ActorMovieRelation.builder().actor(actor).movie(movie).actor(actor).build());
    }
}
