package com.dyns.api_fitness_buddy.controllers;

import com.dyns.api_fitness_buddy.domain.dto.ExerciseDto;
import com.dyns.api_fitness_buddy.domain.entities.Exercise;
import com.dyns.api_fitness_buddy.mappers.Mapper;
import com.dyns.api_fitness_buddy.services.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ExerciseController {

    private final ExerciseService service;
    private final Mapper<Exercise, ExerciseDto> mapper;

    public ExerciseController(
            ExerciseService service,
            Mapper<Exercise, ExerciseDto> mapper
    ) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping(path = "/exercises")
    public ResponseEntity<ExerciseDto> create(@RequestBody ExerciseDto exerciseDto) {
        Exercise entity = mapper.mapToEntity(exerciseDto);
        Exercise savedEntity = service.save(entity);

        return new ResponseEntity<>(
                mapper.mapToDto(savedEntity),
                HttpStatus.CREATED
        );
    }

    @GetMapping(path = "/exercises")
    public List<ExerciseDto> getAll() {
        List<Exercise> exercises = service.findAll();

        return exercises.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/exercises/{id}")
    public ResponseEntity<ExerciseDto> getOne(@PathVariable("id") long id) {
        Optional<Exercise> exercise = service.findOne(id);
        return exercise.map(e -> new ResponseEntity<>(
                        mapper.mapToDto(e),
                        HttpStatus.FOUND)
                )
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/exercises/{id}")
    public ResponseEntity<ExerciseDto> fullUpdate(
            @PathVariable("id") long id,
            @RequestBody ExerciseDto exerciseDto
    ) {
        if (service.doesExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Exercise updatedExercise = service.save(mapper.mapToEntity(exerciseDto));
        return new ResponseEntity<>(
                mapper.mapToDto(updatedExercise),
                HttpStatus.OK
        );
    }

    @PatchMapping(path = "/exercises/{id}")
    public ResponseEntity<ExerciseDto> partialUpdate(
            @PathVariable("id") long id,
            @RequestBody ExerciseDto exerciseDto
    ) {
        if (service.doesExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Exercise updatedExercise = service.partialUpdate(
                id,
                mapper.mapToEntity(exerciseDto)
        );
        return new ResponseEntity<>(
                mapper.mapToDto(updatedExercise),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path = "exercises/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") long id) {
        if (service.doesExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
