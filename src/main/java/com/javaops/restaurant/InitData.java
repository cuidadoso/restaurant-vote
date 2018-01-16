package com.javaops.restaurant;

import com.javaops.restaurant.model.*;
import com.javaops.restaurant.repository.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

import static com.javaops.restaurant.Constants.*;

@Component
@Log
public class InitData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepositorye;
    private final VoteRepository voteRepository;

    @Autowired
    public InitData(final UserRepository userRepository,
                    final DishRepository dishRepository,
                    final MenuRepository menuRepository,
                    final RestaurantRepository restaurantRepositorye,
                    final VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
        this.menuRepository = menuRepository;
        this.restaurantRepositorye = restaurantRepositorye;
        this.voteRepository = voteRepository;
    }

    @Override
    public void run(final String... args) throws Exception {
        // Create users
        userRepository.deleteAll();
        User user1 = User.builder()
                         .name(NAME_1)
                         .email(EMAIL_1)
                         .password(PASSWORD_1)
                         .role(Role.ADMIN)
                         .build();
        User user2 = User.builder()
                         .name(NAME_2)
                         .email(EMAIL_2)
                         .password(PASSWORD_2)
                         .build();
        userRepository.save(Arrays.asList(user1, user2));
        log.info("Users: " + userRepository.findAll());
        // Create dishes
        dishRepository.deleteAll();
        Dish dish1 = Dish.builder()
                         .name(DISH_NAME_1)
                         .price(PRICE_1)
                         .build();
        Dish dish2 = Dish.builder()
                         .name(DISH_NAME_2)
                         .price(PRICE_2)
                         .build();
        dishRepository.save(Arrays.asList(dish1, dish2));
        log.info("Dishes: " + dishRepository.findAll());

        // Create menus
        menuRepository.deleteAll();
        Menu menu1 = Menu.builder()
                         .restaurantId(RESTAURANT_ID_1)
                         .date(DATE_TODAY)
                         .dishes(Collections.emptyList())
                         .build();
        Menu menu2 = Menu.builder()
                         .restaurantId(RESTAURANT_ID_1)
                         .date(DATE_YESTERDAY)
                         .dishes(Collections.emptyList())
                         .build();
        Menu menu3 = Menu.builder()
                         .restaurantId(RESTAURANT_ID_2)
                         .date(DATE_TODAY)
                         .dishes(Collections.emptyList())
                         .build();
        Menu menu4 = Menu.builder()
                         .restaurantId(RESTAURANT_ID_2)
                         .date(DATE_YESTERDAY)
                         .dishes(Arrays.asList(dish1, dish2))
                         .build();
        menuRepository.save(Arrays.asList(menu1, menu2, menu3, menu4));
        log.info("Menus: " + menuRepository.findAll());

        // Create restaurants
        restaurantRepositorye.deleteAll();
        Restaurant restaurant1 = Restaurant.builder()
                                           .name(RESTAURANT_NAME_1)
                                           .address(RESTAURANT_ADDRESS_1)
                                           .build();
        Restaurant restaurant2 = Restaurant.builder()
                                           .name(RESTAURANT_NAME_2)
                                           .address(RESTAURANT_ADDRESS_2)
                                           .build();
        restaurantRepositorye.save(Arrays.asList(restaurant1, restaurant2));
        log.info("Restaurants: " + restaurantRepositorye.findAll());

        // Create votes
        voteRepository.deleteAll();
        Vote vote1 = Vote.builder()
                         .userId(USER_ID_1)
                         .restaurantId(RESTAURANT_ID_1)
                         .time(TIME_TODAY)
                         .build();
        Vote vote2 = Vote.builder()
                         .userId(USER_ID_1)
                         .restaurantId(RESTAURANT_ID_2)
                         .time(TIME_YESTERDAY)
                         .build();
        Vote vote3 = Vote.builder()
                         .userId(USER_ID_2)
                         .restaurantId(RESTAURANT_ID_1)
                         .time(TIME_YESTERDAY)
                         .build();
        Vote vote4 = Vote.builder()
                         .userId(USER_ID_2)
                         .restaurantId(RESTAURANT_ID_2)
                         .time(TIME_TODAY)
                         .build();
        voteRepository.save(Arrays.asList(vote1, vote2, vote3, vote4));
        log.info("Votes: " + voteRepository.findAll());
    }
}
