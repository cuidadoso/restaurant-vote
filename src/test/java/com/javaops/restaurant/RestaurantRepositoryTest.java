package com.javaops.restaurant;

import com.javaops.restaurant.models.Restaurant;
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
        Restaurant restaurant1 = Restaurant.builder()
                                           .name("Restaurant1")
                                           .address("Address1")
                                           .build();
        Restaurant restaurant2 = Restaurant.builder()
                                           .name("Restaurant2")
                                           .address("Address2")
                                           .build();
        assertThat(restaurant1.getId()).isNull();
        assertThat(restaurant2.getId()).isNull();
        id1 = repository.save(restaurant1)
                        .getId();
        log.info("Restaurant has created: " + restaurant1);
        id2 = repository.save(restaurant2)
                        .getId();
        log.info("Restaurant has created: " + restaurant2);
        assertThat(repository.findById(id1)).isNotNull();
        assertThat(repository.findById(id2)).isNotNull();
    }

    @Test
    public void testDataUpdate() {
        List<Restaurant> restaurants = repository.findAll();
        assertThat(restaurants).hasSize(2);
        assertThat(restaurants).extracting("name", "address")
                               .contains(tuple("Restaurant1", "Address1"),
                                         tuple("Restaurant2", "Address2"));

        Restaurant restaurantA = repository.findByName("Restaurant1");
        assertThat(restaurantA.getName()).isEqualTo("Restaurant1");
        restaurantA.setName("Restaurant1 Updated");
        repository.save(restaurantA);

        Restaurant restaurantB = repository.findByNameContains("Updated")
                                           .get(0);
        assertThat(restaurantB).isNotNull();
        assertThat(restaurantB.getName()).isEqualTo("Restaurant1 Updated");

        Restaurant restaurantC = repository.findById(id1);
        assertThat(restaurantC).isNotNull();
        assertThat(restaurantC.getName()).as("check %s's name", restaurantC.getName())
                                   .isEqualTo("Restaurant1 Updated");
        assertThat(restaurantC.getAddress()).as("check %s's address", restaurantC.getName())
                                            .isEqualTo("Address1");

        Restaurant restaurantD = repository.findByAddress("Address2");
        assertThat(restaurantD.getName()).as("check %s's name", restaurantD.getName())
                                   .isEqualTo("Restaurant2");
        assertThat(restaurantD.getAddress()).as("check %s's address", restaurantD.getName())
                                    .isEqualTo("Address2");
        restaurantD.setAddress("Address2 Updated");
        repository.save(restaurantD);

        Restaurant restaurantE = repository.findByAddressContains("Updated")
                                           .get(0);
        assertThat(restaurantE.getName()).as("check %s's name", restaurantE.getName())
                                         .isEqualTo("Restaurant2");
        assertThat(restaurantE.getAddress()).as("check %s's address", restaurantE.getName())
                                            .isEqualTo("Address2 Updated");
    }

    @After
    public void tearDown() {
        repository.deleteAll();
        log.info("--> Restaurant test end.");
    }
}
