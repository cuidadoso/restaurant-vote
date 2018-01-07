package com.javaops.restaurant.controller;

import com.javaops.restaurant.model.User;
import com.javaops.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController extends EntityController<User>{
    private final UserRepository repository;

    @Autowired
    public UserController(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected MongoRepository<User, String> getRepository() {
        return repository;
    }

    @GetMapping(params = {"email"},
                produces = MediaType.APPLICATION_JSON_VALUE)
    public User getByEmail(@RequestParam("email") String email) {
        return repository.findByEmail(email);
    }
}
