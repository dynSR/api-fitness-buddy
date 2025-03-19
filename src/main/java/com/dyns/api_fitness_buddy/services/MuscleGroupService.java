package com.dyns.api_fitness_buddy.services;

import com.dyns.api_fitness_buddy.domain.dto.MuscleGroupDto;
import com.dyns.api_fitness_buddy.domain.entities.MuscleGroup;

public interface MuscleGroupService {
    MuscleGroup save(MuscleGroup muscleGroup);
}

