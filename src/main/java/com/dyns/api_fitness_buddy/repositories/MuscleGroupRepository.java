package com.dyns.api_fitness_buddy.repositories;

import com.dyns.api_fitness_buddy.domain.entities.MuscleGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuscleGroupRepository extends CrudRepository<MuscleGroup, Long> {}
