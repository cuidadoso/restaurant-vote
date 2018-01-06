package com.javaops.restaurant;

import com.javaops.restaurant.models.Menu;
import com.javaops.restaurant.repository.MenuRepository;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class MenuRepositoryTest {
    private final String RESTAURANT_ID_1 = "111111";
    private final String RESTAURANT_ID_2 = "222222";
    private final LocalDate DATE_1 = LocalDate.now();
    private final LocalDate DATE_2 = DATE_1.minusDays(1);

    @Autowired
    private MenuRepository repository;
    private String id1;
    private String id2;
    private String id3;
    private String id4;

    @Before
    public void setUp() {
        log.info("--> Menu test start.");
        Menu menu1 = Menu.builder()
                         .restaurantId(RESTAURANT_ID_1)
                         .date(DATE_1)
                         .build();
        Menu menu2 = Menu.builder()
                         .restaurantId(RESTAURANT_ID_1)
                         .date(DATE_2)
                         .build();
        Menu menu3 = Menu.builder()
                         .restaurantId(RESTAURANT_ID_2)
                         .date(DATE_1)
                         .build();
        Menu menu4 = Menu.builder()
                         .restaurantId(RESTAURANT_ID_2)
                         .date(DATE_2)
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
        assertThat(repository.findById(id1)).isNotNull();
        assertThat(repository.findById(id2)).isNotNull();
        assertThat(repository.findById(id3)).isNotNull();
        assertThat(repository.findById(id4)).isNotNull();

        List<Menu> menus = repository.findAll();
        assertThat(menus).hasSize(4);
        assertThat(menus).extracting("restaurantId", "date")
                          .contains(tuple(RESTAURANT_ID_1, DATE_1),
                                    tuple(RESTAURANT_ID_1, DATE_2),
                                    tuple(RESTAURANT_ID_2, DATE_1),
                                    tuple(RESTAURANT_ID_2, DATE_2));

        List<Menu> menusA = repository.findByRestaurantId(RESTAURANT_ID_1);
        assertThat(menusA).hasSize(2);
        assertThat(menusA).extracting("restaurantId", "date")
                         .contains(tuple(RESTAURANT_ID_1, DATE_1),
                                   tuple(RESTAURANT_ID_1, DATE_2));

        List<Menu> menusB = repository.findByDate(DATE_1);
        assertThat(menusB).hasSize(2);
        assertThat(menusB).extracting("restaurantId", "date")
                          .contains(tuple(RESTAURANT_ID_1, DATE_1),
                                    tuple(RESTAURANT_ID_2, DATE_1));

        Menu menuA = repository.findByRestaurantIdAndDate(RESTAURANT_ID_1, DATE_1);
        assertThat(menuA.getRestaurantId()).isEqualTo(RESTAURANT_ID_1);
        assertThat(menuA.getDate()).isEqualTo(DATE_1);

        Menu menuB = repository.findById(id1);
        assertThat(menuB).isNotNull();
        assertThat(menuB.getRestaurantId()).isEqualTo(RESTAURANT_ID_1);
        assertThat(menuB.getDate()).isEqualTo(DATE_1);
    }

    @After
    public void tearDown() {
        repository.deleteAll();
        log.info("--> Menu test end.");
    }
}
