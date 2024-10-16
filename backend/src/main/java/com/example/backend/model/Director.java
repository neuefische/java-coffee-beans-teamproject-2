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
public class Director {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
