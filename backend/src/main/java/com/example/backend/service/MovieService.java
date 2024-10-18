package com.example.backend.service;

import com.example.backend.model.Movie;
import com.example.backend.model.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    private final IdService idService;

    public Movie createMovie(Movie movie) {
        movie.setId(idService.getRandomId());

        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getWatchedMovies() {
        return movieRepository.findAllByIsWatchedIsTrue();
    }

    public List<Movie> getWishlistedMovies() {
        return movieRepository.findAllByIsWatchedIsFalse();
    }

    public Movie getMovieById(String movieId) {
        return movieRepository.findById(movieId).orElseThrow();
    }

    public void deleteMovie(String movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        movieRepository.delete(movie);
    }

    public Movie updateMovie(Movie movie) {
        movieRepository.findById(movie.getId()).orElseThrow();
        return movieRepository.save(movie);
    }
}
