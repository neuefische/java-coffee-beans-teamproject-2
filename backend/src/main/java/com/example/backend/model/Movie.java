package com.example.backend.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    private Double rating;

    @ManyToMany(cascade = { CascadeType.ALL })
    Set<Actor> actors;

    @ManyToMany(cascade = { CascadeType.ALL })
    Set<Director> directors;

    private String name;
}
