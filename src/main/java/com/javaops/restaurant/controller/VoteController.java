package com.javaops.restaurant.controller;

import com.javaops.restaurant.model.Restaurant;
import com.javaops.restaurant.model.User;
import com.javaops.restaurant.model.Vote;
import com.javaops.restaurant.repository.RestaurantRepository;
import com.javaops.restaurant.repository.UserRepository;
import com.javaops.restaurant.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/votes")
public class VoteController extends AbstractResponseHelper<Vote> {
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Vote>> getAll() {
        return getListResponse(repository.findAll());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> vote(@RequestBody final Vote vote) {
        if(!validateEntity(vote)) {
            return  new ResponseEntity<>(vote, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Vote> votes = repository.findByUserIdAndRestaurantId(vote.getUserId(), vote.getRestaurantId());
        if(votes.isEmpty()) {
            vote.setId(null);
            vote.setTime(LocalDateTime.now());
            return getOneResponse(repository.save(vote));
        }
        LocalDate nowDate = LocalDate.now();
        LocalTime deadLineTime = LocalTime.of(11, 0);
        List<Vote> filteredVotes = votes.stream()
                                        .filter(v -> {
                                            LocalDateTime dateTime = v.getTime();
                                            return dateTime.toLocalDate().equals(nowDate) &&
                                                   dateTime.toLocalTime().isBefore(deadLineTime);
                                        })
                                        .collect(Collectors.toList());
        if(!filteredVotes.isEmpty()) {
            repository.delete(filteredVotes);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(params = {"user_id"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Vote>> getByUser(@RequestParam("user_id") String userId) {
        return getListResponse(repository.findByUserId(userId));
    }

    @GetMapping(params = {"restaurant_id"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Vote>> getByRestaurant(@RequestParam("restaurant_id") String restaurantId) {
        return getListResponse(repository.findByRestaurantId(restaurantId));
    }

    @GetMapping(params = {"user_id", "restaurant_id"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Vote>> getByUserAndRestaurant(@RequestParam("user_id") String userId,
                                             @RequestParam("restaurant_id") String restaurantId) {
        return getListResponse(repository.findByUserIdAndRestaurantId(userId, restaurantId));
    }

    protected boolean validateEntity(final Vote vote) {
        if(vote == null) {
            return false;
        }
        String restaurantId = vote.getRestaurantId();
        if(restaurantId == null || restaurantId.isEmpty()) {
           return false;
        }
        Restaurant restaurant = restaurantRepository.findOne(restaurantId);
        if(restaurant == null) {
            return false;
        }
        String userId = vote.getUserId();
        if(userId == null || userId.isEmpty()) {
            return false;
        }
        User user = userRepository.findOne(userId);
        return user != null;
    }
}
