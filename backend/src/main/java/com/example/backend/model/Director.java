package com.example.backend.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Director {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = { CascadeType.ALL })
    private Set<Movie> movies;

    private String name;
}
