package com.javaops.restaurant.controller;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class EntityController<T> {

    protected abstract MongoRepository<T, String> getRepository();

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    protected List<T> getAll() {
        return getRepository().findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public T get(@PathVariable("id") final String id) {
        T entity = getRepository().findOne(id);
        validateEntity(entity);
        return getRepository().findOne(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public T create(@RequestBody T entity) {
        validateEntity(entity);
        return getRepository().save(entity);
    }

    @PutMapping(value = "/{id}",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public T update(@PathVariable final String id, @RequestBody final T entity) {
        validateEntity(entity);
        return getRepository().save(entity);
    }

    @DeleteMapping(value = "/{id}",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable final String id) {
        T entity = getRepository().findOne(id);
        validateEntity(entity);
        getRepository().delete(id);
    }

    // TODO implement custom RuntimeException
    private void validateEntity(final T entity) throws RuntimeException {
        if(entity == null) {
            throw new RuntimeException();
        }
    }
}
