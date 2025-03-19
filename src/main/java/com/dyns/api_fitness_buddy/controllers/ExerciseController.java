package com.dyns.api_fitness_buddy.controllers;

import com.dyns.api_fitness_buddy.domain.dto.ExerciseDto;
import com.dyns.api_fitness_buddy.domain.entities.Exercise;
import com.dyns.api_fitness_buddy.mappers.Mapper;
import com.dyns.api_fitness_buddy.services.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExerciseController {

    private final ExerciseService service;
    private final Mapper<Exercise, ExerciseDto> mapper;

    public ExerciseController(ExerciseService service, Mapper<Exercise, ExerciseDto> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping(path = "/exercises")
    public ResponseEntity<ExerciseDto> create(@RequestBody ExerciseDto exercise) {
        Exercise entity = mapper.mapToEntity(exercise);
        Exercise savedEntity = service.save(entity);
        return new ResponseEntity<>(mapper.mapToDto(savedEntity), HttpStatus.CREATED);
    }
}
