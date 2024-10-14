package com.example.backend.service;

import com.example.backend.model.ActorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;

}
