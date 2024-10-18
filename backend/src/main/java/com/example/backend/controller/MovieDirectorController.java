package com.example.backend.controller;

import com.example.backend.dto.DirectorResponse;
import com.example.backend.dto.MovieDirectorRequest;
import com.example.backend.dto.MovieDirectorResponse;
import com.example.backend.model.MovieDirectorRelation;
import com.example.backend.service.MovieDirectorService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movie-director")
public class MovieDirectorController {

    private final MovieDirectorService movieDirectorService;

    @PostMapping()
    public MovieDirectorResponse addRelation(@RequestBody @NonNull MovieDirectorRequest movieDirectorRequest) {
        MovieDirectorRelation relation = movieDirectorService.addRelation(movieDirectorRequest.movieId(), movieDirectorRequest.directorId());

        return MovieDirectorResponse.from(relation);
    }

    @DeleteMapping()
    public void deleteRelation(@RequestBody @NonNull MovieDirectorRequest movieDirectorRequest) {
        movieDirectorService.removeRelation(movieDirectorRequest.movieId(), movieDirectorRequest.directorId());
    }

    @GetMapping("/{movieId}")
    public List<DirectorResponse> getActorsByMovieId(@PathVariable @NonNull String movieId) {
        return movieDirectorService.getDirectorsByMovieId(movieId).stream().map(DirectorResponse::from).toList();
    }

    /**
     * Prevents the frontend fallback response from being returned if the request path is invalid.
     *
     * @throws NoResourceFoundException
     */
    @GetMapping("/")
    public void getByMovieIdInvalid() throws NoResourceFoundException {
        throw new NoResourceFoundException(HttpMethod.GET, "/api/movie-director/");
    }
}
