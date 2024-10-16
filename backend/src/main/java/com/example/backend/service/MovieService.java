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

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow();
    }

    public void deleteMovie(Long movieId) {
        movieRepository.findById(movieId).orElseThrow();
        movieRepository.deleteById(movieId);
    }

    public Movie updateMovie(Movie movie) {
        movieRepository.findById(movie.getId()).orElseThrow();

        return movieRepository.save(movie);
    }
}
