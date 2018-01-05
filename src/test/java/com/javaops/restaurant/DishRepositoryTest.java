package com.javaops.restaurant;

import com.javaops.restaurant.models.Dish;
import com.javaops.restaurant.repository.DishRepository;
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
public class DishRepositoryTest {

    @Autowired
    private DishRepository repository;
    private String id1;
    private String id2;

    @Before
    public void setUp() {
        Dish dish1 = new Dish("Soup Tomato", 1000L);
        Dish dish2 = new Dish("Potato Free", 900L);
        assertThat(dish1.getId()).isNull();
        assertThat(dish2.getId()).isNull();
        id1 = repository.save(dish1)
                        .getId();
        id2 = repository.save(dish2)
                        .getId();
        assertThat(repository.findById(id1)).isNotNull();
        assertThat(repository.findById(id2)).isNotNull();
    }

    @Test
    public void testDataUpdate() {
        List<Dish> dishes = repository.findAll();
        assertThat(dishes).hasSize(2);
        assertThat(dishes).extracting("name", "price")
                          .contains(tuple("Soup Tomato", 1000L),
                                    tuple("Potato Free", 900L));

        Dish dishA = repository.findByName("Soup Tomato");
        assertThat(dishA.getName()).isEqualTo("Soup Tomato");
        dishA.setName("Soup With Meat");
        repository.save(dishA);

        Dish dishB = repository.findByNameContains("Meat")
                               .get(0);
        assertThat(dishB).isNotNull();
        assertThat(dishB.getName()).isEqualTo("Soup With Meat");

        Dish dishC = repository.findById(id1);
        assertThat(dishC).isNotNull();
        assertThat(dishC.getName()).as("check %s's name", dishC.getName())
                                   .isEqualTo("Soup With Meat");
        assertThat(dishC.getPrice()).as("check %s's price", dishC.getName())
                                    .isEqualTo(1000L);

        Dish dishD = repository.findByPrice(900L)
                               .get(0);
        assertThat(dishD.getName()).as("check %s's name", dishD.getName())
                                   .isEqualTo("Potato Free");
        assertThat(dishD.getPrice()).as("check %s's price", dishD.getName())
                                    .isEqualTo(900L);
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }
}
