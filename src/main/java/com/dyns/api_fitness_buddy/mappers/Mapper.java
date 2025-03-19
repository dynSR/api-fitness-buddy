package com.dyns.api_fitness_buddy.mappers;

public interface Mapper<Entity, Dto> {

    /**
     * This function maps an entity from a dto.
     * @param dto
     * @return an entity based the dto provided.
     */
    Entity mapToEntity(Dto dto);

    /**
     * This function maps an entity to a dto.
     * @param entity
     * @return a dto based on the entity provided.
     */
    Dto mapToDto(Entity entity);
}
