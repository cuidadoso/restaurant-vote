package com.javaops.restaurant.repository;

import com.javaops.restaurant.models.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DishRepository extends MongoRepository<Dish, String> {
    Dish findById(final String id);

    Dish findByName(final String name);

    List<Dish> findByNameContains(final String name);

    List<Dish> findByPrice(final Long price);
}
