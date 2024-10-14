package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Actor {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = { CascadeType.ALL })
    private Set<Movie> movies;

    private String name;
}
