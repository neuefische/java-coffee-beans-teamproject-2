package com.example.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Data
public class MovieDirectorRelation {
    @Id
    private String id;

    private String directorId;

    private String movieId;
}

