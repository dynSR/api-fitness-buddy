package com.dyns.api_fitness_buddy.repositories;

import com.dyns.api_fitness_buddy.domain.entities.MuscleGroup;
import com.dyns.api_fitness_buddy.fixtures.impl.MuscleGroupFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class MuscleGroupRepositoryIntegrationTests {

    private final MuscleGroupRepository underTest;
    private final MuscleGroupFixture fixture;

    @Autowired
    public MuscleGroupRepositoryIntegrationTests(MuscleGroupRepository underTest, MuscleGroupFixture fixture) {
        this.underTest = underTest;
        this.fixture = fixture;
    }

    @Test
    public void givenMuscleGroup_whenSaved_thenCanBeRetrieved() {
        MuscleGroup muscleGroup = fixture.getOne();

        underTest.save(muscleGroup);

        Optional<MuscleGroup> result = underTest.findById(muscleGroup.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(muscleGroup);
    }

    @Test
    public void givenDuplicateMuscleGroup_whenSaved_thenThrowsDataIntegrityViolationException() {
        MuscleGroup muscleGroup1 = fixture.getOne();
        MuscleGroup muscleGroup2 = fixture.getOne();

        underTest.save(muscleGroup1);
        try {
            underTest.save(muscleGroup2);
        } catch (DataIntegrityViolationException e) {
            assertThat(e.getMessage()).contains("Unique index or primary key violation");
        }
    }

    @Test
    public void givenMuscleGroup_whenNameIsUpdated_thenNameIsPersisted() {
        MuscleGroup muscleGroup = underTest.save(fixture.getOne());
        assertThat(underTest.existsById(muscleGroup.getId())).isTrue();

        String newName = "Updated";
        muscleGroup.setName(newName);
        underTest.save(muscleGroup);

        Optional<MuscleGroup> result = underTest.findById(muscleGroup.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(newName);
    }

    @Test
    public void givenMuscleGroup_whenDeleted_thenItCannotBeFound() {
        MuscleGroup muscleGroup = underTest.save(fixture.getOne());
        assertThat(underTest.existsById(muscleGroup.getId())).isTrue();

        underTest.delete(muscleGroup);
        assertThat(underTest.existsById(muscleGroup.getId())).isFalse();
    }
}
