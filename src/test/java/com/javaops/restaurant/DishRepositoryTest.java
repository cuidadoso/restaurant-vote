package com.javaops.restaurant;

import com.javaops.restaurant.models.Dish;
import com.javaops.restaurant.repository.DishRepository;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class DishRepositoryTest {
    private final String DISH_NAME_1 = "Soup Tomato";
    private final String DISH_NAME_2 = "Potato Free";
    private final String DISH_NAME_3 = "Soup With Meat";
    private final String SEARCH_WORD = "Meat";
    private final Long PRICE_1 = 1000L;
    private final Long PRICE_2 = 900L;

    @Autowired
    private DishRepository repository;
    private String id1;
    private String id2;

    @Before
    public void setUp() {
        log.info("--> Dish test start.");
        Dish dish1 = Dish.builder()
                         .name(DISH_NAME_1)
                         .price(PRICE_1)
                         .build();
        Dish dish2 = Dish.builder()
                         .name(DISH_NAME_2)
                         .price(PRICE_2)
                         .build();
        assertThat(dish1.getId()).isNull();
        assertThat(dish2.getId()).isNull();
        id1 = repository.save(dish1)
                        .getId();
        log.info("Dish has created: " + dish1);
        id2 = repository.save(dish2)
                        .getId();
        log.info("Dish has created: " + dish2);
    }

    @Test
    public void testDataUpdate() {
        assertThat(repository.findById(id1)).isNotNull();
        assertThat(repository.findById(id2)).isNotNull();

        List<Dish> dishes = repository.findAll();
        assertThat(dishes).hasSize(2);
        assertThat(dishes).extracting("name", "price")
                          .contains(tuple(DISH_NAME_1, PRICE_1),
                                    tuple(DISH_NAME_2, PRICE_2));

        Dish dishA = repository.findByName(DISH_NAME_1);
        assertThat(dishA.getName()).isEqualTo(DISH_NAME_1);
        dishA.setName(DISH_NAME_3);
        repository.save(dishA);

        Dish dishB = repository.findByNameContains(SEARCH_WORD)
                               .get(0);
        assertThat(dishB).isNotNull();
        assertThat(dishB.getName()).isEqualTo(DISH_NAME_3);

        Dish dishC = repository.findById(id1);
        assertThat(dishC).isNotNull();
        assertThat(dishC.getName()).as("check %s's name", dishC.getName())
                                   .isEqualTo(DISH_NAME_3);
        assertThat(dishC.getPrice()).as("check %s's price", dishC.getName())
                                    .isEqualTo(PRICE_1);

        Dish dishD = repository.findByPrice(PRICE_2)
                               .get(0);
        assertThat(dishD.getName()).as("check %s's name", dishD.getName())
                                   .isEqualTo(DISH_NAME_2);
        assertThat(dishD.getPrice()).as("check %s's price", dishD.getName())
                                    .isEqualTo(PRICE_2);
    }

    @After
    public void tearDown() {
        repository.deleteAll();
        log.info("--> Dish test end.");
    }
}
