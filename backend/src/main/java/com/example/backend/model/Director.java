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
public class Director {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<Movie> movies;

    private String name;
}
