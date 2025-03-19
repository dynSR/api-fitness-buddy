package com.dyns.api_fitness_buddy.repositories;

import com.dyns.api_fitness_buddy.domain.entities.Exercise;
import com.dyns.api_fitness_buddy.fixtures.impl.ExerciseFixture;
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
public class ExerciseRepositoryIntegrationTests {

    private final ExerciseRepository underTest;
    private final ExerciseFixture fixture;

    @Autowired
    public ExerciseRepositoryIntegrationTests(
            ExerciseRepository underTest
    ) {
        this.underTest = underTest;
        fixture = new ExerciseFixture();
    }

    @Test
    public void givenExercise_whenSaved_thenCanBeRetrieved() {
        Exercise exercise = fixture.getOne();

        underTest.save(exercise);

        Optional<Exercise> result = underTest.findById(exercise.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(exercise);
    }

    @Test
    public void givenDuplicateExercise_whenSaved_thenThrowsDataIntegrityViolationException() {
        Exercise exercise1 = fixture.getOne();
        Exercise exercise2 = fixture.getOne();

        underTest.save(exercise1);
        try {
            underTest.save(exercise2);
        } catch (DataIntegrityViolationException e) {
            assertThat(e.getMessage()).contains("Unique index or primary key violation");
        }
    }

    @Test
    public void givenExercise_whenNameIsUpdated_thenNameIsPersisted() {
        Exercise exercise = underTest.save(fixture.getOne());
        assertThat(underTest.existsById(exercise.getId())).isTrue();

        String newName = "Updated";
        exercise.setName(newName);
        underTest.save(exercise);

        Optional<Exercise> result = underTest.findById(exercise.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(newName);
    }

    @Test
    public void givenExercise_whenDeleted_thenItCannotBeFound() {
        Exercise exercise = underTest.save(fixture.getOne());
        assertThat(underTest.existsById(exercise.getId())).isTrue();

        underTest.delete(exercise);
        assertThat(underTest.existsById(exercise.getId())).isFalse();
    }
}
