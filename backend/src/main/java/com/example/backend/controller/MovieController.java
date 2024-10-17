package com.example.backend.controller;

import com.example.backend.dto.ActorResponse;
import com.example.backend.dto.CreateMovieRequest;
import com.example.backend.dto.MovieResponse;
import com.example.backend.model.Movie;
import com.example.backend.service.MovieService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public MovieResponse save(@RequestBody @NotNull CreateMovieRequest movieRequest) {
        Movie movie = movieRequest.toMovie();
        return MovieResponse.from(movieService.createMovie(movie));
    }

    @GetMapping
    public List<MovieResponse> getAll() {
        return movieService.getAllMovies().stream().map(MovieResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MovieResponse getByMovieId(@PathVariable @NonNull Long id) {
        Movie searchedMovie = movieService.getMovieById(id);
        return MovieResponse.from(searchedMovie);
    }

    @PutMapping("/{id}")
    public MovieResponse update(@RequestBody @NotNull CreateMovieRequest movieRequest, @PathVariable Long id) {
        Movie movie = movieRequest.toMovie();
        movie.setId(id);
        return MovieResponse.from(movieService.updateMovie(movie));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
    movieService.deleteMovie(id);
    }
}
