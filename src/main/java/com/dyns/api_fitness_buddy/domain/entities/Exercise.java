package com.dyns.api_fitness_buddy.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents the exercise entity throughout the application.
 * <p>
 * This entity defines the characteristics of an exercise, such as its name,
 * associated muscle groups, difficulty level, and required equipment.
 * It is used to structure workout programs and track user progress effectively.
 * <p>
 * TODO: Add the serializable interface and study what it does, why we use it.
 *
 * @author dyns
 * @version 0.0.1
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "exercises", uniqueConstraints = {
        @UniqueConstraint(name = "UX_exercises_name", columnNames = {"name"})
})
public class Exercise implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercise_id_seq")
    private Long id;

    @Column(length = 150, nullable = false)
    private String name;
}
