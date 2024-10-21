package com.example.backend.service;

import com.example.backend.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieDirectorService {
    private final IdService idService;

    private final DirectorRepository directorRepository;

    private final MovieRepository movieRepository;
    private final MovieDirectorRelationRepository movieDirectorRelationRepository;

    public List<Director> getDirectorsByMovieId(String movieId) {
        return movieDirectorRelationRepository.findByMovieId(movieId)
                .stream()
                .map(MovieDirectorRelation::getDirectorId)
                .map(directorRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public MovieDirectorRelation addRelation(String movieId, String directorId) {
        return movieDirectorRelationRepository
                .findByMovieIdAndDirectorId(movieId, directorId).orElseGet(
                        () -> {
                            Movie movie = movieRepository.findById(movieId).orElseThrow();
                            Director director = directorRepository.findById(directorId).orElseThrow();
                            return movieDirectorRelationRepository.save(MovieDirectorRelation.builder()
                                    .id(idService.getRandomId())
                                    .movieId(movie.getId())
                                    .directorId(director.getId())
                                    .build());
                        }
                );
    }

    public void removeRelation(String movieId, String directorId) {
        MovieDirectorRelation relation = movieDirectorRelationRepository
                .findByMovieIdAndDirectorId(movieId, directorId)
                .orElseThrow();

        movieDirectorRelationRepository.delete(relation);
    }
}
