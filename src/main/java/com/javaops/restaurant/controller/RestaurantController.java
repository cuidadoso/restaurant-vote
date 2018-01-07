package com.javaops.restaurant.controller;

import com.javaops.restaurant.model.Restaurant;
import com.javaops.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/restaurants")
public class RestaurantController extends EntityController<Restaurant>{
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantController(final RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    protected MongoRepository<Restaurant, String> getRepository() {
        return repository;
    }

    @GetMapping(params = {"name"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByName(@RequestParam("name") String name) {
        return repository.findByName(name);
    }

    @GetMapping(params = {"name_contains"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getByNameContains(@RequestParam("name_contains") String name) {
        return repository.findByNameContains(name);
    }

    @GetMapping(params = {"address"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByAddress(@RequestParam("address") String address) {
        return repository.findByAddress(address);
    }

    @GetMapping(params = {"address_contains"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByAddressContains(@RequestParam("address_contains") String address) {
        return repository.findByAddress(address);
    }
}
