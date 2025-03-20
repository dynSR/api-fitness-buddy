package com.dyns.api_fitness_buddy.fixtures.impl;

import com.dyns.api_fitness_buddy.domain.entities.Exercise;
import com.dyns.api_fitness_buddy.fixtures.Fixture;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public final class ExerciseFixture implements Fixture<Exercise> {
    @Override
    public Exercise getOne() {
        return Exercise.builder()
                .id(null) // id is automatically generated
                .name("Exercise")
                .build();
    }

    /**
     * This method is used to generate 6 different exercises.
     * @return a list of exercice entities.
     */
    @Override
    public List<Exercise> getMany() {
        List<Exercise> exercises = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            exercises.add(
                    Exercise.builder()
                            .id(null) // id is automatically generated
                            .name(String.format("Exercise %d", i))
                            .build()
            );
        }

        return exercises;
    }
}
