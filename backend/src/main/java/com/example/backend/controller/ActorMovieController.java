package com.example.backend.controller;

import com.example.backend.dto.ActorResponse;
import com.example.backend.dto.IdRequest;
import com.example.backend.service.ActorMovieService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/actor-movie")
public class ActorMovieController {

    private final ActorMovieService actorMovieService;

    @PostMapping("/{movieId}")
    public void addActorById(@PathVariable @NonNull Long movieId, @RequestBody IdRequest idRequest) {
        actorMovieService.addActor(movieId, idRequest.id());
    }

    @GetMapping("/{movieId}")
    public List<ActorResponse> getActorsByMovieId(@PathVariable @NonNull Long movieId) {
        return actorMovieService.getActorsByMovieId(movieId).stream().map(ActorResponse::from).toList();
    }
}
