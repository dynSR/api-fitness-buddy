package com.dyns.api_fitness_buddy.mappers.impl;

import com.dyns.api_fitness_buddy.domain.dto.MuscleGroupDto;
import com.dyns.api_fitness_buddy.domain.entities.MuscleGroup;
import com.dyns.api_fitness_buddy.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MuscleGroupMapperImpl implements Mapper<MuscleGroup, MuscleGroupDto> {

    private final ModelMapper modelMapper;

    public MuscleGroupMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MuscleGroup mapToEntity(MuscleGroupDto muscleGroupDto) {
        return modelMapper.map(muscleGroupDto, MuscleGroup.class);
    }

    @Override
    public MuscleGroupDto mapToDto(MuscleGroup muscleGroup) {
        return modelMapper.map(muscleGroup, MuscleGroupDto.class);
    }
}
