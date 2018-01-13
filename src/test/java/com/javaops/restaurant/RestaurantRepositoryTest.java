package com.javaops.restaurant;

import com.javaops.restaurant.model.Restaurant;
import com.javaops.restaurant.repository.RestaurantRepository;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.javaops.restaurant.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository repository;
    private String id1;
    private String id2;

    @Before
    public void setUp() {
        log.info("--> Restaurant test start.");
        repository.deleteAll();
        Restaurant restaurant1 = Restaurant.builder()
                                           .name(RESTAURANT_NAME_1)
                                           .address(RESTAURANT_ADDRESS_1)
                                           .build();
        Restaurant restaurant2 = Restaurant.builder()
                                           .name(RESTAURANT_NAME_2)
                                           .address(RESTAURANT_ADDRESS_2)
                                           .build();
        assertThat(restaurant1.getId()).isNull();
        assertThat(restaurant2.getId()).isNull();
        id1 = repository.save(restaurant1)
                        .getId();
        log.info("Restaurant has created: " + restaurant1);
        id2 = repository.save(restaurant2)
                        .getId();
        log.info("Restaurant has created: " + restaurant2);
    }

    @Test
    public void testDataUpdate() {
        assertThat(repository.findOne(id1)).isNotNull();
        assertThat(repository.findOne(id2)).isNotNull();

        List<Restaurant> restaurants = repository.findAll();
        assertThat(restaurants).hasSize(2);
        assertThat(restaurants).extracting("name", "address")
                               .contains(tuple(RESTAURANT_NAME_1, RESTAURANT_ADDRESS_1),
                                         tuple(RESTAURANT_NAME_2, RESTAURANT_ADDRESS_2));

        Restaurant restaurantA = repository.findByName(RESTAURANT_NAME_1);
        assertThat(restaurantA.getName()).isEqualTo(RESTAURANT_NAME_1);
        restaurantA.setName(RESTAURANT_NAME_UPD);
        repository.save(restaurantA);

        Restaurant restaurantB = repository.findByNameContains(SEARCH_WORD_RESTAURANT)
                                           .get(0);
        assertThat(restaurantB).isNotNull();
        assertThat(restaurantB.getName()).isEqualTo(RESTAURANT_NAME_UPD);

        Restaurant restaurantC = repository.findOne(id1);
        assertThat(restaurantC).isNotNull();
        assertThat(restaurantC.getName()).as("check %s's name", restaurantC.getName())
                                   .isEqualTo(RESTAURANT_NAME_UPD);
        assertThat(restaurantC.getAddress()).as("check %s's address", restaurantC.getName())
                                            .isEqualTo(RESTAURANT_ADDRESS_1);

        Restaurant restaurantD = repository.findByAddress(RESTAURANT_ADDRESS_2);
        assertThat(restaurantD.getName()).as("check %s's name", restaurantD.getName())
                                   .isEqualTo(RESTAURANT_NAME_2);
        assertThat(restaurantD.getAddress()).as("check %s's address", restaurantD.getName())
                                    .isEqualTo(RESTAURANT_ADDRESS_2);
        restaurantD.setAddress(RESTAURANT_ADDRESS_UPD);
        repository.save(restaurantD);

        Restaurant restaurantE = repository.findByAddressContains(SEARCH_WORD_RESTAURANT)
                                           .get(0);
        assertThat(restaurantE.getName()).as("check %s's name", restaurantE.getName())
                                         .isEqualTo(RESTAURANT_NAME_2);
        assertThat(restaurantE.getAddress()).as("check %s's address", restaurantE.getName())
                                            .isEqualTo(RESTAURANT_ADDRESS_UPD);
    }

    @After
    public void tearDown() {
        repository.deleteAll();
        log.info("--> Restaurant test end.");
    }
}
