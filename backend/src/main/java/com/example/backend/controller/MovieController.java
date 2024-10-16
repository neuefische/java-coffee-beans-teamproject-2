package com.example.backend.controller;

import com.example.backend.dto.CreateMovieRequest;
import com.example.backend.dto.MovieResponse;
import com.example.backend.model.Movie;
import com.example.backend.service.MovieService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public MovieResponse save(@RequestBody @NotNull CreateMovieRequest movieRequest){
        Movie movie = movieRequest.toMovie();
        return MovieResponse.from(movieService.createMovie(movie));
    }
    @GetMapping
    public List<MovieResponse> getAll(){
        return movieService.getAllMovies().stream().map(MovieResponse::from).collect(Collectors.toList());
    }
}
