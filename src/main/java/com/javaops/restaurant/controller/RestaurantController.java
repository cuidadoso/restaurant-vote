package com.javaops.restaurant.controller;

import com.javaops.restaurant.model.Restaurant;
import com.javaops.restaurant.repository.RestaurantRepository;
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
@RequestMapping(value = "/api/v1/restaurants")
public class RestaurantController extends EntityController<Restaurant> {
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
    public ResponseEntity<Restaurant> getByName(@RequestParam("name") String name) {
        return getOneResponse(repository.findByName(name));
    }

    @GetMapping(params = {"name_contains"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Restaurant>> getByNameContains(@RequestParam("name_contains") String name) {
        return getListResponse(repository.findByNameContains(name));
    }

    @GetMapping(params = {"address"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> getByAddress(@RequestParam("address") String address) {
        return getOneResponse(repository.findByAddress(address));
    }

    @GetMapping(params = {"address_contains"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Restaurant>> getByAddressContains(@RequestParam("address_contains") String address) {
        return getListResponse(repository.findByAddressContains(address));
    }
}
