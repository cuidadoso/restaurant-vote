package com.javaops.restaurant.repository;

import com.javaops.restaurant.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    Restaurant findByName(final String name);

    List<Restaurant> findByNameContains(final String name);

    Restaurant findByAddress(final String address);

    List<Restaurant> findByAddressContains(final String address);
}
