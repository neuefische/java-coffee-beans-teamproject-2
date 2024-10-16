package com.example.backend.service;

import com.example.backend.model.Actor;
import com.example.backend.model.ActorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;

    private final IdService idService;

    public Actor createActor(Actor actor) {
        actor.setId(idService.getRandomId());

        return actorRepository.save(actor);
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public List<Actor> getActorsByPrefix(String prefix) {
        return actorRepository.findByNameStartingWith(prefix);
    }
}
