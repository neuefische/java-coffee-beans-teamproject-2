package com.example.backend.service;

import com.example.backend.model.Director;
import com.example.backend.model.DirectorRepository;
import com.example.backend.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Director getDirectorById(Long directorId) {
        return directorRepository.findById(directorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with id " + directorId + " not found"));
    }

    public void deleteDirector(Long directorId) {
        if (directorRepository.existsById(directorId)) {
            directorRepository.deleteById(directorId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with id " + directorId + " does not exist");
        }
    }
}