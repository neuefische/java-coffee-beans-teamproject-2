package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Actor> actors;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Director> directors;

    private String name;
}
