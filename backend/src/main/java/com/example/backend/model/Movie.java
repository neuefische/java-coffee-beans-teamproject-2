package com.example.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Data
@Document("Movie")
public class Movie {
    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 10;

    @Id
    private String id;

    private Integer rating;

    private boolean isWatched;

    private String name;
}
