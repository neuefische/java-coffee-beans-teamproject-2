package com.example.backend.service;

import com.example.backend.model.Movie;
import com.example.backend.model.MovieRepository;
import com.example.backend.model.Rating;
import com.example.backend.model.RatingRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    private final RatingRepository ratingRepository;

    private final IdService idService;

    public Movie createMovie(Movie movie) {
        movie.setId(idService.getRandomId());

        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> getWatchedMovies(@NonNull String userId) {
        List<String> movieIds = ratingRepository.findAllByUserIdAndIsWatchedIsTrue(userId).stream()
                .map(Rating::getMovieId).toList();

        return movieRepository.findAllById(movieIds);
    }

    public List<Movie> getWishlistedMovies(@NonNull String userId) {
        List<String> movieIds = ratingRepository.findAllByUserIdAndIsWatchedIsFalse(userId).stream()
                .map(Rating::getMovieId).toList();

        return movieRepository.findAllById(movieIds);
    }

    public Movie getMovieById(String movieId) {
        return movieRepository.findById(movieId).orElseThrow();
    }

    public void deleteMovie(String movieId) {
        movieRepository.findById(movieId).ifPresent(movieRepository::delete);
    }

    public Movie updateMovie(Movie movie) {
        movieRepository.findById(movie.getId()).orElseThrow();
        return movieRepository.save(movie);
    }
}
