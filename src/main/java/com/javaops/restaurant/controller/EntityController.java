package com.javaops.restaurant.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

public abstract class EntityController<T>  extends AbstractResponseHelper<T> {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    protected ResponseEntity<Collection<T>> getAll() {
        return getListResponse(getRepository().findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> get(@PathVariable("id") final String id) {
        return getOneResponse(getRepository().findOne(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> create(@RequestBody T entity) {
        return createResponse(entity);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> update(@RequestBody final T entity) {
        return updateResponse(entity);
    }

    @DeleteMapping(value = "/{id}",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> delete(@PathVariable final String id, Principal principal) {
        return deleteResponse(getRepository().findOne(id));
    }
}
