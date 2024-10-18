package com.example.backend.dto;

import lombok.NonNull;

public record MovieDirectorRequest(@NonNull String movieId, @NonNull String directorId) {
}
