package com.example.backend.service;

import com.example.backend.model.Actor;
import com.example.backend.model.Director;
import com.example.backend.model.DirectorRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;

    public Director createDirector(Director director) {
        return directorRepository.save(director);
    }

    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    public Director getDirectorById(String directorId) {
        return directorRepository.findById(directorId).orElseThrow();
    }

    public void deleteDirector(String directorId) {
        directorRepository.findById(directorId).ifPresent(directorRepository::delete);
    }

    public List<Director> getDirectorsByPrefix(String prefix) {
        return directorRepository.findByNameStartingWith(prefix);
    }
}