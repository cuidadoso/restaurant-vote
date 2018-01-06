package com.javaops.restaurant.repository;

import com.javaops.restaurant.models.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VoteRepository extends MongoRepository<Vote, String> {
    Vote findById(final String id);

    List<Vote> findByUserId(final String UserId);

    List<Vote> findByRestaurantId(final String restaurantId);

    List<Vote> findByUserIdAndRestaurantId(final String userId, final String restaurantId);
}
