package com.javaops.restaurant.controller;

import com.javaops.restaurant.model.Role;
import com.javaops.restaurant.model.User;
import com.javaops.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

public abstract class EntityController<T>  extends AbstractResponseHelper<T> {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    protected ResponseEntity<Collection<T>> getAll() {
        return getListResponse(getRepository().findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> get(@PathVariable("id") final String id) {
        return getOneResponse(getRepository().findOne(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> create(T entity, Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName());
        if(Role.ADMIN.equals(currentUser.getRole())) {
            return createResponse(entity);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> update(@RequestBody final T entity, Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName());
        if(Role.ADMIN.equals(currentUser.getRole())) {
            return updateResponse(entity);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(value = "/{id}",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> delete(@PathVariable final String id, Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName());
        if(Role.ADMIN.equals(currentUser.getRole())) {
            return deleteResponse(getRepository().findOne(id));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
