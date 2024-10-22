package com.example.backend.service;

import com.example.backend.model.Movie;
import com.example.backend.model.MovieRepository;
import com.example.backend.model.Rating;
import com.example.backend.model.RatingRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
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

    public List<Pair<Rating, Movie>> getWatchedMovies(@NonNull String userId) {
        List<Rating> ratings = ratingRepository.findAllByUserIdAndIsWatchedIsTrue(userId);

        return getMovieRatings(ratings);
    }

    public List<Pair<Rating, Movie>> getWishlistedMovies(@NonNull String userId) {
        List<Rating> ratings = ratingRepository.findAllByUserIdAndIsWatchedIsFalse(userId);

        return getMovieRatings(ratings);
    }

    private List<Pair<Rating, Movie>> getMovieRatings(List<Rating> ratings) {
        List<String> movieIds = ratings.stream().map(Rating::getMovieId).toList();
        List<Movie> movies = movieRepository.findAllById(movieIds);

        return ratings.stream().map(
                        (Rating rating) -> {
                            return Pair.of(
                                    rating,
                                    movies.stream().filter(
                                            (Movie movie) -> movie.getId().equals(rating.getMovieId())
                                    ).findFirst().orElse(null)
                            );
                        }
                ).filter((ratingMoviePair -> ratingMoviePair.getSecond() != null))
                .toList();
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
