package com.example.backend.controller;

import com.example.backend.dto.ActorResponse;
import com.example.backend.dto.MovieActorRequest;
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
    public void addRelation(@RequestBody @NonNull MovieActorRequest movieActorRequest) {
        movieActorService.addActor(movieActorRequest.movieId(), movieActorRequest.actorId());
    }

    @DeleteMapping()
    public void deleteRelation(@RequestBody @NonNull MovieActorRequest movieActorRequest) {
        movieActorService.removeActor(movieActorRequest.movieId(), movieActorRequest.actorId());
    }

    @GetMapping("/{movieId}")
    public List<ActorResponse> getActorsByMovieId(@PathVariable @NonNull Long movieId) {
        return movieActorService.getActorsByMovieId(movieId).stream().map(ActorResponse::from).toList();
    }

    /**
     * Prevents the frontend fallback response from being returned if the request path is invalid.
     *
     * @throws NoResourceFoundException
     */
    @GetMapping("/")
    public void getByMovieIdInvalid() throws NoResourceFoundException {
        throw new NoResourceFoundException(HttpMethod.GET, "/api/actor-movie/");
    }}
