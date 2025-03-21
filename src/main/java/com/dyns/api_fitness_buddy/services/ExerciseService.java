package com.dyns.api_fitness_buddy.services;

import com.dyns.api_fitness_buddy.domain.entities.Exercise;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {

    Exercise save(Exercise exercise);

    List<Exercise> findAll();

    Optional<Exercise> findOne(long id);

    Exercise partialUpdate(long id, Exercise exercise);

    void delete(long id);

    boolean doesExist(long id);

}
