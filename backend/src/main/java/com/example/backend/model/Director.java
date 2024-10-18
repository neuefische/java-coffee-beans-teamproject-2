package com.example.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Data
@Document("Director")
public class Director {

    @Id
    private String id;

    private String name;
}
