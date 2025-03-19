package com.dyns.api_fitness_buddy.services.impl;

import com.dyns.api_fitness_buddy.domain.entities.Exercise;
import com.dyns.api_fitness_buddy.repositories.ExerciseRepository;
import com.dyns.api_fitness_buddy.services.ExerciseService;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository repository;

    public ExerciseServiceImpl(ExerciseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Exercise save(Exercise exercise) {
        return this.repository.save(exercise);
    }
}
