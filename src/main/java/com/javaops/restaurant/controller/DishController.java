package com.javaops.restaurant.controller;

import com.javaops.restaurant.model.Dish;
import com.javaops.restaurant.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api/v1/dishes")
public class DishController extends EntityController<Dish> {
    private final DishRepository repository;

    @Autowired
    public DishController(final DishRepository repository) {
        this.repository = repository;
    }

    @Override
    protected MongoRepository<Dish, String> getRepository() {
        return repository;
    }

    @GetMapping(params = {"name"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> getByName(@RequestParam("name") String name) {
        return getOneResponse(repository.findByName(name));
    }

    @GetMapping(params = {"name_contains"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Dish>> getByNameContains(@RequestParam("name_contains") String name) {
        return getListResponse(repository.findByNameContains(name));
    }

    @GetMapping(params = {"price"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Collection<Dish>> getByPrice(@RequestParam("price") Long price) {
        return getListResponse(repository.findByPrice(price));
    }
}
