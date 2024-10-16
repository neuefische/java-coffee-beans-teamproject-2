package com.example.backend.service;

import com.example.backend.model.Movie;
import com.example.backend.model.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (movieRepository.existsById(movieId)) {
            return movieRepository.findById(movieId).get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with id " + movieId + " does not exist");
    }

    public void deleteMovie(Long movieId) {
        if (movieRepository.existsById(movieId)) {
            movieRepository.deleteById(movieId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with id " + movieId + " does not exist");
        }
    }
}
