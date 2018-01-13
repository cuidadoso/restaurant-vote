package com.javaops.restaurant;

import com.javaops.restaurant.model.Dish;
import com.javaops.restaurant.model.Menu;
import com.javaops.restaurant.repository.DishRepository;
import com.javaops.restaurant.repository.MenuRepository;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import static com.javaops.restaurant.Constants.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class MenuRepositoryTest {
    @Autowired
    private MenuRepository repository;
    @Autowired
    private DishRepository dishRepository;
    private String id1;
    private String id2;
    private String id3;
    private String id4;

    @Before
    public void setUp() {
        log.info("--> Menu test start.");
        repository.deleteAll();
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
        id1 = dishRepository.save(dish1)
                        .getId();
        log.info("Dish has created: " + dish1);
        id2 = dishRepository.save(dish2)
                        .getId();
        log.info("Dish has created: " + dish2);
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
        assertThat(menu1.getId()).isNull();
        assertThat(menu2.getId()).isNull();
        assertThat(menu3.getId()).isNull();
        assertThat(menu4.getId()).isNull();
        id1 = repository.save(menu1)
                        .getId();
        log.info("Menu has created: " + menu1);
        id2 = repository.save(menu2)
                        .getId();
        log.info("Menu has created: " + menu2);
        id3 = repository.save(menu3)
                        .getId();
        log.info("Menu has created: " + menu3);
        id4 = repository.save(menu4)
                        .getId();
        log.info("Menu has created: " + menu4);
    }

    @Test
    public void testDataUpdate() {
        assertThat(repository.findOne(id1)).isNotNull();
        assertThat(repository.findOne(id2)).isNotNull();
        assertThat(repository.findOne(id3)).isNotNull();
        assertThat(repository.findOne(id4)).isNotNull();

        List<Menu> menus = repository.findAll();
        assertThat(menus).hasSize(4);
        assertThat(menus).extracting("restaurantId", "date")
                          .contains(tuple(RESTAURANT_ID_1, DATE_TODAY),
                                    tuple(RESTAURANT_ID_1, DATE_YESTERDAY),
                                    tuple(RESTAURANT_ID_2, DATE_TODAY),
                                    tuple(RESTAURANT_ID_2, DATE_YESTERDAY));

        List<Menu> menusA = repository.findByRestaurantId(RESTAURANT_ID_1);
        assertThat(menusA).hasSize(2);
        assertThat(menusA).extracting("restaurantId", "date")
                         .contains(tuple(RESTAURANT_ID_1, DATE_TODAY),
                                   tuple(RESTAURANT_ID_1, DATE_YESTERDAY));

        List<Menu> menusB = repository.findByDate(DATE_TODAY);
        assertThat(menusB).hasSize(2);
        assertThat(menusB).extracting("restaurantId", "date")
                          .contains(tuple(RESTAURANT_ID_1, DATE_TODAY),
                                    tuple(RESTAURANT_ID_2, DATE_TODAY));

        Menu menuA = repository.findByRestaurantIdAndDate(RESTAURANT_ID_1, DATE_TODAY);
        assertThat(menuA.getRestaurantId()).isEqualTo(RESTAURANT_ID_1);
        assertThat(menuA.getDate()).isEqualTo(DATE_TODAY);
        assertThat(menuA.getDishes()).hasSize(0);

        Menu menuB = repository.findOne(id4);
        assertThat(menuB).isNotNull();
        assertThat(menuB.getRestaurantId()).isEqualTo(RESTAURANT_ID_2);
        assertThat(menuB.getDate()).isEqualTo(DATE_YESTERDAY);
        List<Dish> dishes = menuB.getDishes();
        assertThat(dishes).hasSize(2);
        assertThat(dishes).extracting("name", "price")
                          .contains(tuple(DISH_NAME_1, PRICE_1),
                                    tuple(DISH_NAME_2, PRICE_2));
    }

    @After
    public void tearDown() {
        repository.deleteAll();
        dishRepository.deleteAll();
        log.info("--> Menu test end.");
    }
}
