package com.example.backend.controller;

import com.example.backend.dto.*;
import com.example.backend.model.Director;
import com.example.backend.service.DirectorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/director")
public class DirectorController {
    private final DirectorService directorService;

    @PostMapping
    public DirectorResponse save(@RequestBody @Valid @NotNull CreateDirectorRequest directorRequest){
        Director director = directorRequest.toDirector();
        return DirectorResponse.from(directorService.createDirector(director));
    }
    @GetMapping
    public List<DirectorResponse> getAll(){
        return directorService.getAllDirectors().stream().map(DirectorResponse::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DirectorResponse getDirectorByMovieId(@PathVariable @NonNull Long id) {
        Director searchedMovie = directorService.getDirectorById(id);
        return DirectorResponse.from(searchedMovie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        directorService.deleteDirector(id);
    }
}
