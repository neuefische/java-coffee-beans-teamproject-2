package com.example.backend.dto;

import lombok.NonNull;

public record MovieActorRequest(@NonNull String movieId, @NonNull String actorId) {
}
