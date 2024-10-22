package com.example.backend.controller;

import com.example.backend.dto.ActorResponse;
import com.example.backend.dto.MovieActorRequest;
import com.example.backend.dto.MovieActorResponse;
import com.example.backend.model.MovieActorRelation;
import com.example.backend.service.MovieActorService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/movie-actor")
public class MovieActorController {

    private final MovieActorService movieActorService;

    @PostMapping()
    public MovieActorResponse addRelation(@RequestBody @NonNull MovieActorRequest movieActorRequest) {
        MovieActorRelation relation = movieActorService.addActor(movieActorRequest.movieId(), movieActorRequest.actorId());

        return MovieActorResponse.from(relation);
    }

    @DeleteMapping()
    public void deleteRelation(@RequestBody @NonNull MovieActorRequest movieActorRequest) {
        movieActorService.removeActor(movieActorRequest.movieId(), movieActorRequest.actorId());
    }

    @DeleteMapping("/{movieId}")
    public void deleteRelationsByMovieId(@PathVariable @NonNull String movieId) {
        movieActorService.removeRelationsByMovieId(movieId);
    }

    @GetMapping("/{movieId}")
    public List<ActorResponse> getActorsByMovieId(@PathVariable @NonNull String movieId) {
        return movieActorService.getActorsByMovieId(movieId).stream().map(ActorResponse::from).toList();
    }

    /**
     * Prevents the frontend fallback response from being returned if the request path is invalid.
     *
     * @throws NoResourceFoundException
     */
    @GetMapping("/")
    public void getByMovieIdInvalid() throws NoResourceFoundException {
        throw new NoResourceFoundException(HttpMethod.GET, "/api/movie-actor/");
    }
}
