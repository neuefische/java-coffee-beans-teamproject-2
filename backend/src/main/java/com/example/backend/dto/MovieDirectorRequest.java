package com.example.backend.dto;

import lombok.NonNull;

public record MovieDirectorRequest(@NonNull Long movieId, @NonNull String directorId) {
}
