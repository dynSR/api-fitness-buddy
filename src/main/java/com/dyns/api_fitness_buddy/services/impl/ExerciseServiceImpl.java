package com.dyns.api_fitness_buddy.services.impl;

import com.dyns.api_fitness_buddy.domain.dto.ExerciseDto;
import com.dyns.api_fitness_buddy.domain.entities.Exercise;
import com.dyns.api_fitness_buddy.repositories.ExerciseRepository;
import com.dyns.api_fitness_buddy.services.ExerciseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<Exercise> findAll() {
        return StreamSupport.stream(
                        repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Exercise> findOne(long id) {
        return repository.findById(id);
    }

    @Override
    public Exercise partialUpdate(long id, Exercise exercise) {
        exercise.setId(id);

        return repository.findById(id).map(e -> {
            Optional.ofNullable(exercise.getName()).ifPresent(e::setName);
            repository.save(e);
            return e;
        }).orElseThrow(() ->  new RuntimeException("Exercise does not exist."));
    }

    @Override
    public boolean doesExist(long id) {
        return !repository.existsById(id);
    }
}
