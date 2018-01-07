package com.javaops.restaurant.repository;

import com.javaops.restaurant.model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VoteRepository extends MongoRepository<Vote, String> {
    List<Vote> findByUserId(final String UserId);

    List<Vote> findByRestaurantId(final String restaurantId);

    List<Vote> findByUserIdAndRestaurantId(final String userId, final String restaurantId);
}
