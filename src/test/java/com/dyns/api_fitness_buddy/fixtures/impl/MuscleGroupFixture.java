package com.dyns.api_fitness_buddy.fixtures.impl;

import com.dyns.api_fitness_buddy.domain.entities.MuscleGroup;
import com.dyns.api_fitness_buddy.fixtures.Fixture;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public final class MuscleGroupFixture implements Fixture<MuscleGroup> {

    private final Set<String> MUSCLE_GROUPS = new HashSet<>(
            Arrays.asList(
                    "Traps", "Shoulders", "Chest", "Biceps", "Forearm", "Abs",
                    "Quads", "Calves", "Lats", "Triceps", "Glutes", "Hamstrings", "Calves"
            )
    );

    MuscleGroup getOneWithName(String name) {
        return MuscleGroup.builder()
                .id(null) // id is automatically generated
                .name(name.isEmpty() ? "Empty Name" : name)
                .build();
    }

    @Override
    public MuscleGroup getOne() {
        int index = new Random().nextInt(MUSCLE_GROUPS.size());
        String muscleGroupName = MUSCLE_GROUPS.toArray()[index].toString();

        return MuscleGroup.builder()
                .id(null) // id is automatically generated
                .name(muscleGroupName)
                .build();
    }

    @Override
    public List<MuscleGroup> getMany() {
        List<MuscleGroup> muscleGroups = new ArrayList<>();
        for (int i = 0; i < MUSCLE_GROUPS.size(); i++) {
            muscleGroups.add(
                    getOneWithName(MUSCLE_GROUPS.toArray()[i].toString())
            );
        }

        return muscleGroups;
    }
}
