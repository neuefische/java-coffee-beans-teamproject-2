package com.example.backend.service;

import com.example.backend.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieActorService {
    private final MovieActorRelationRepository movieActorRelationRepository;

    private final IdService idService;

    private final ActorRepository actorRepository;

    private final MovieRepository movieRepository;

    public List<Actor> getActorsByMovieId(String movieId) {
        return movieActorRelationRepository.findByMovieId(movieId)
                .stream()
                .map(MovieActorRelation::getActorId)
                .map(id -> actorRepository.findById(id).orElseThrow())
                .toList();
    }

    public MovieActorRelation addActor(String movieId, String actorId) {
        return movieActorRelationRepository
                .findByMovieIdAndActorId(movieId, actorId).orElseGet(
                        () -> {
                            Movie movie = movieRepository.findById(movieId).orElseThrow();
                            Actor actor = actorRepository.findById(actorId).orElseThrow();
                            return movieActorRelationRepository.save(MovieActorRelation.builder()
                                    .id(idService.getRandomId())
                                    .movieId(movie.getId())
                                    .actorId(actor.getId())
                                    .build());
                        }
                );
    }

    public void removeActor(String movieId, String actorId) {
        MovieActorRelation relation = movieActorRelationRepository
                .findByMovieIdAndActorId(movieId, actorId)
                .orElseThrow();

        movieActorRelationRepository.delete(relation);
    }
}
