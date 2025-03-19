package com.dyns.api_fitness_buddy.mappers.impl;

import com.dyns.api_fitness_buddy.domain.dto.ExerciseDto;
import com.dyns.api_fitness_buddy.domain.entities.Exercise;
import com.dyns.api_fitness_buddy.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapperImpl implements Mapper<Exercise, ExerciseDto> {

    private final ModelMapper modelMapper;

    public ExerciseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Exercise mapToEntity(ExerciseDto exerciseDto) {
        return modelMapper.map(exerciseDto, Exercise.class);
    }

    @Override
    public ExerciseDto mapToDto(Exercise exercise) {
        return modelMapper.map(exercise, ExerciseDto.class);
    }
}
