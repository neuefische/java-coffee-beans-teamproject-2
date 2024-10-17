package com.example.backend.dto;

import lombok.NonNull;

public record MovieActorRequest(@NonNull Long movieId, @NonNull Long actorId) {
}
