package com.javaops.restaurant.controller;

import com.javaops.restaurant.model.Restaurant;
import com.javaops.restaurant.model.User;
import com.javaops.restaurant.model.Vote;
import com.javaops.restaurant.repository.RestaurantRepository;
import com.javaops.restaurant.repository.UserRepository;
import com.javaops.restaurant.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/votes")
public class VoteController extends EntityController<Vote>{
    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public VoteController(final VoteRepository repository,
                          final RestaurantRepository restaurantRepository,
                          final UserRepository userRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected MongoRepository<Vote, String> getRepository() {
        return repository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote create(@RequestBody Vote vote) {
        validateVote(vote);
        return getRepository().save(vote);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote update(@PathVariable final String id, @RequestBody final Vote vote) {
        validateVote(vote);
        return getRepository().save(vote);
    }

    @GetMapping(params = {"user_id"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByUser(@RequestParam("user_id") String userId) {
        return repository.findByUserId(userId);
    }

    @GetMapping(params = {"restaurant_id"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByRestaurant(@RequestParam("restaurant_id") String restaurantId) {
        return repository.findByRestaurantId(restaurantId);
    }

    @GetMapping(params = {"user_id", "restaurant_id"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByUserAndRestaurant(@RequestParam("user_id") String userId,
                                             @RequestParam("restaurant_id") String restaurantId) {
        return repository.findByUserIdAndRestaurantId(userId, restaurantId);
    }

    // TODO implement custom RuntimeException
    private void validateVote(final Vote vote) {
        if(vote == null) {
            throw new RuntimeException();
        }
        String restaurantId = vote.getRestaurantId();
        if(restaurantId == null || restaurantId.isEmpty()) {
            throw new RuntimeException();
        }
        Restaurant restaurant = restaurantRepository.findOne(restaurantId);
        if(restaurant == null) {
            throw new RuntimeException();
        }
        String userId = vote.getUserId();
        if(userId == null || userId.isEmpty()) {
            throw new RuntimeException();
        }
        User user = userRepository.findOne(userId);
        if(user == null) {
            throw new RuntimeException();
        }
    }
}
