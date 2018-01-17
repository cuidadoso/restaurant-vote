package com.javaops.restaurant.controller;

import com.javaops.restaurant.model.User;
import com.javaops.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController extends EntityController<User> {
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
    public ResponseEntity<User> getByEmail(@RequestParam("email") String email) {
        return getOneResponse(repository.findByEmail(email));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(User entity, Principal principal) {
        return createResponse(entity);

    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@RequestBody final User entity, Principal principal) {
        User currentUser = repository.findByEmail(principal.getName());
        if(entity.getId().equals(currentUser.getId())) {
            return updateResponse(entity);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> delete(@PathVariable final String id, Principal principal) {
        User currentUser = repository.findByEmail(principal.getName());
        if (id.equals(currentUser.getId())) {
            return deleteResponse(repository.findOne(id));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
