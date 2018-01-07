package com.javaops.restaurant.repository;

import com.javaops.restaurant.model.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository extends MongoRepository<Menu, String> {
    List<Menu> findByRestaurantId(final String restaurantId);

    List<Menu> findByDate(final LocalDate date);

    Menu findByRestaurantIdAndDate(final String restaurantId, final LocalDate date);
}
