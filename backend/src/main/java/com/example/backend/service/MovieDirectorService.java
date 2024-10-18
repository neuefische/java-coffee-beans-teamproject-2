package com.example.backend.service;

import com.example.backend.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieDirectorService {

    private final DirectorRepository directorRepository;

    private final MovieRepository movieRepository;
    private final MovieDirectorRelationRepository movieDirectorRelationRepository;


    public void addRelation(Long movieId, Long directorId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Director director = directorRepository.findById(directorId).orElseThrow();
        movieDirectorRelationRepository.save(MovieDirectorRelation.builder().director(director).movie(movie).build());
    }

    public void removeRelation(Long movieId, Long directorId) {
        MovieDirectorRelation relation = movieDirectorRelationRepository
                .findByMovieIdAndDirectorId(movieId, directorId)
                .orElseThrow();

        movieDirectorRelationRepository.delete(relation);
    }
}
