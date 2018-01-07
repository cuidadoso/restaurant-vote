package com.javaops.restaurant.repository;

import com.javaops.restaurant.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(final String email);
}
