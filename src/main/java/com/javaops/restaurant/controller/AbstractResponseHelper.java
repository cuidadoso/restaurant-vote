package com.javaops.restaurant.controller;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

public abstract class AbstractResponseHelper<T> {

    protected abstract MongoRepository<T, String> getRepository();

    protected ResponseEntity<T> getOneResponse(final T entity) {
        if (entity != null) {
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    protected ResponseEntity<Collection<T>> getListResponse(final List<T> entities) {
        if (entities != null && !entities.isEmpty()) {
            return new ResponseEntity<>(entities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    protected ResponseEntity<T> createResponse (final T entity) {
        if(validateEntity(entity)) {
            return new ResponseEntity<>(getRepository().save(entity), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(entity, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    protected ResponseEntity<T> updateResponse(final T entity) {
        if(validateEntity(entity)) {
            return new ResponseEntity<>(getRepository().save(entity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(entity, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    protected ResponseEntity<T> deleteResponse (final T entity) {
        if(validateEntity(entity)) {
            getRepository().delete(entity);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(entity, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    protected boolean validateEntity(final T entity) throws RuntimeException {
        return entity != null;
    }
}
