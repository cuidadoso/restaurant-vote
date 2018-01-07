package com.javaops.restaurant.controller;

import com.javaops.restaurant.model.Menu;
import com.javaops.restaurant.model.Restaurant;
import com.javaops.restaurant.repository.MenuRepository;
import com.javaops.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/menus")
public class MenuController extends EntityController<Menu>{
    private final MenuRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuController(final MenuRepository repository,
                          final RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    protected MongoRepository<Menu, String> getRepository() {
        return repository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu create(@RequestBody Menu menu) {
        validateMenu(menu);
        return getRepository().save(menu);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu update(@PathVariable final String id, @RequestBody final Menu menu) {
        validateMenu(menu);
        return getRepository().save(menu);
    }

    @GetMapping(params = {"restaurant_id"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getByRestaurant(@RequestParam("restaurant_id") String restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }

    @GetMapping(params = {"date"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getByDate(@RequestParam("date") LocalDate date) {
        return repository.findByDate(date);
    }

    @GetMapping(params = {"restaurant_id", "date"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu getByRestaurantAndDate(@RequestParam("restaurant_id") String restaurantId,
                                             @RequestParam("date") LocalDate date) {
        return repository.findByRestaurantIdAndDate(restaurantId, date);
    }

    // TODO implement custom RuntimeException
    private void validateMenu(final Menu menu) {
        if(menu == null) {
            throw new RuntimeException();
        }
        String restaurantId = menu.getRestaurantId();
        if(restaurantId == null) {
            throw new RuntimeException();
        }
        Restaurant restaurant = restaurantRepository.findOne(menu.getRestaurantId());
        if(restaurant == null) {
            throw new RuntimeException();
        }
    }
}
