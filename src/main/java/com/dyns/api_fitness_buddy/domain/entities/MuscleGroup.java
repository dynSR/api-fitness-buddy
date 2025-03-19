package com.dyns.api_fitness_buddy.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents the muscle group entity throughout the application.
 * <p>
 * It is commonly used to categorize different exercises and ensure
 * that workouts are balanced across various body parts.
 * <p>
 * TODO: Add the serializable interface and study what is does, why we use it.
 *
 * @author dyns
 * @version 0.0.1
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "muscle_groups", uniqueConstraints = {
        @UniqueConstraint(name = "UX_muscleGroups_name", columnNames = {"name"})
})
public class MuscleGroup implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "muscle_group_id_seq")
    private Long id;

    @Column(length = 150, nullable = false)
    private String name;
}
