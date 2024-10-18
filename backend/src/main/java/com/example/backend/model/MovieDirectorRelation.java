package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Data
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"director_id", "movie_id"})
        }
)
public class MovieDirectorRelation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Director director;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Movie movie;
}

