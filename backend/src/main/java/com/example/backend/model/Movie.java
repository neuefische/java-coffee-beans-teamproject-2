package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Data
public class Movie {
    public static final int MIN_RATING = 0;
    public static final int MAX_RATING = 10;
    @Id
    @GeneratedValue
    private Long id;

    private Integer rating;

    private boolean isWatched;

    private String name;
}
