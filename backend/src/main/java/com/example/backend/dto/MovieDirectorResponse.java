package com.example.backend.dto;

import com.example.backend.model.MovieActorRelation;
import com.example.backend.model.MovieDirectorRelation;
import lombok.Builder;

@Builder
public record MovieDirectorResponse(String movieId, String directorId) {
    public static MovieDirectorResponse from(MovieDirectorRelation relation) {
        return MovieDirectorResponse.builder().movieId(relation.getMovieId()).directorId(relation.getDirectorId()).build();
    }
}
