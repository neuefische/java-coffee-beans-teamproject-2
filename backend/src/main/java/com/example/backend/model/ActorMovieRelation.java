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
                @UniqueConstraint(columnNames = {"actor_id", "movie_id"})
        }
)
public class ActorMovieRelation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Actor actor;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Movie movie;
}
