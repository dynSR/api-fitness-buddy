package com.dyns.api_fitness_buddy.repositories;

import com.dyns.api_fitness_buddy.domain.entities.Exercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {}
