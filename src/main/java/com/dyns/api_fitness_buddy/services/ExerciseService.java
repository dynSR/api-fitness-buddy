package com.dyns.api_fitness_buddy.services;

import com.dyns.api_fitness_buddy.domain.dto.ExerciseDto;
import com.dyns.api_fitness_buddy.domain.entities.Exercise;

public interface ExerciseService {

    Exercise save(Exercise exercise);
}
