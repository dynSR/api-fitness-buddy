package com.dyns.api_fitness_buddy.fixtures;

import java.util.List;

public interface Fixture<T> {
    T getOne();
    List<T> getMany();
}
