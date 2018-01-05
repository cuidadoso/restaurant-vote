package com.javaops.restaurant.repository;

import com.javaops.restaurant.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findById(final String id);

    User findByEmail(final String email);
}
