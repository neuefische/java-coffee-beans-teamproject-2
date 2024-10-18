package com.example.backend.dto;

import com.example.backend.model.MovieActorRelation;
import lombok.Builder;

@Builder
public record MovieActorResponse(String movieId, String actorId) {
    public static MovieActorResponse from(MovieActorRelation relation) {
        return MovieActorResponse.builder().movieId(relation.getMovieId()).actorId(relation.getActorId()).build();
    }
}
