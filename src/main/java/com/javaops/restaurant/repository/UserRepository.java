package com.javaops.restaurant.repository;

import com.javaops.restaurant.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAll();
    User findByEmail(final String email);
}
