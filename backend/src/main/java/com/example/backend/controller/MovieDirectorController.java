package com.example.backend.controller;

import com.example.backend.dto.ActorResponse;
import com.example.backend.dto.MovieActorRequest;
import com.example.backend.dto.MovieDirectorRequest;
import com.example.backend.service.MovieActorService;
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
    public void addRelation(@RequestBody @NonNull MovieDirectorRequest movieDirectorRequest) {
        movieDirectorService.addRelation(movieDirectorRequest.movieId(), movieDirectorRequest.directorId());
    }

    @DeleteMapping()
    public void deleteRelation(@RequestBody @NonNull MovieDirectorRequest movieDirectorRequest) {
        movieDirectorService.removeRelation(movieDirectorRequest.movieId(), movieDirectorRequest.directorId());
    }

    /**
     * Prevents the frontend fallback response from being returned if the request path is invalid.
     *
     * @throws NoResourceFoundException
     */
    @GetMapping("/")
    public void getByMovieIdInvalid() throws NoResourceFoundException {
        throw new NoResourceFoundException(HttpMethod.GET, "/api/actor-movie/");
    }
}
