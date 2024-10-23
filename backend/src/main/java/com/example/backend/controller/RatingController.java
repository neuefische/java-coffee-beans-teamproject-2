package com.example.backend.controller;

import com.example.backend.dto.CreateRatingRequest;
import com.example.backend.dto.RatingResponse;
import com.example.backend.model.Movie;
import com.example.backend.model.Rating;
import com.example.backend.service.MovieService;
import com.example.backend.service.RatingService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;
    private final MovieService movieService;

    @PostMapping
    public void save(@RequestBody @NotNull CreateRatingRequest request, @AuthenticationPrincipal OAuth2User user) {
        String login = user.getAttributes().get("login").toString();
        Rating rating = request.toRating(login);

        ratingService.save(rating);
    }

    @GetMapping("/{movieId}")
    public RatingResponse get(@AuthenticationPrincipal OAuth2User user, @PathVariable String movieId) {
        String login = user.getAttributes().get("login").toString();
        Rating rating = ratingService.get(login, movieId);

        return RatingResponse.from(rating);
    }
    @GetMapping("/init")
    public String innitTestData (@AuthenticationPrincipal OAuth2User user) {
        Movie movie1 =  Movie.builder().name("Movie 1").build();
        movieService.createMovie(movie1);
        Rating rating1 = Rating.builder().rating(5).movieId(movie1.getId()).userId(user.getAttributes().get("login").toString()).isWatched(true).build();
        Movie movie2 = Movie.builder().name("Movie 2").build();
        movieService.createMovie(movie2);
        Rating rating2 = Rating.builder().rating(3).movieId(movie2.getId()).userId(user.getAttributes().get("login").toString()).isWatched(false).build();
        Movie movie3 = Movie.builder().name("Movie 3").build();
        movieService.createMovie(movie3);
        Rating rating3 = Rating.builder().rating(7).movieId(movie3.getId()).userId(user.getAttributes().get("login").toString()).isWatched(true).build();
        Movie movie4 = Movie.builder().name("Movie 4").build();
        movieService.createMovie(movie4);
        Rating rating4 = Rating.builder().rating(8).movieId(movie4.getId()).userId(user.getAttributes().get("login").toString()).isWatched(false).build();

        ratingService.save(rating1);
        ratingService.save(rating2);
        ratingService.save(rating3);
        ratingService.save(rating4);
        return "Hello";
    }
}
