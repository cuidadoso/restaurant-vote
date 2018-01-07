package com.javaops.restaurant;

import com.javaops.restaurant.model.Vote;
import com.javaops.restaurant.repository.VoteRepository;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class VoteRepositoryTest {
    private final String USER_ID_1 = "U1";
    private final String USER_ID_2 = "U2";
    private final String RESTAURANT_ID_1 = "R1";
    private final String RESTAURANT_ID_2 = "R2";
    private LocalDateTime TIME_TODAY = LocalDateTime.now();
    private LocalDateTime TIME_YESTERDAY = TIME_TODAY.minusDays(1);

    @Autowired
    private VoteRepository repository;
    private String id1;
    private String id2;
    private String id3;
    private String id4;

    @Before
    public void setUp() {
        log.info("--> Vote test start.");
        Vote vote1 = Vote.builder()
                         .userId(USER_ID_1)
                         .restaurantId(RESTAURANT_ID_1)
                         .time(TIME_TODAY)
                         .build();
        Vote vote2 = Vote.builder()
                         .userId(USER_ID_1)
                         .restaurantId(RESTAURANT_ID_2)
                         .time(TIME_YESTERDAY)
                         .build();
        Vote vote3 = Vote.builder()
                         .userId(USER_ID_2)
                         .restaurantId(RESTAURANT_ID_1)
                         .time(TIME_YESTERDAY)
                         .build();
        Vote vote4 = Vote.builder()
                         .userId(USER_ID_2)
                         .restaurantId(RESTAURANT_ID_2)
                         .time(TIME_TODAY)
                         .build();
        assertThat(vote1.getId()).isNull();
        assertThat(vote2.getId()).isNull();
        assertThat(vote3.getId()).isNull();
        assertThat(vote4.getId()).isNull();
        id1 = repository.save(vote1)
                        .getId();
        log.info("Vote has created: " + vote1);
        id2 = repository.save(vote2)
                        .getId();
        log.info("Vote has created: " + vote2);
        id3 = repository.save(vote3)
                        .getId();
        log.info("Vote has created: " + vote3);
        id4 = repository.save(vote4)
                        .getId();
        log.info("Vote has created: " + vote4);
    }

    @Test
    public void testDataUpdate() {
        assertThat(repository.findOne(id1)).isNotNull();
        assertThat(repository.findOne(id2)).isNotNull();
        assertThat(repository.findOne(id3)).isNotNull();
        assertThat(repository.findOne(id4)).isNotNull();

        Vote vote = repository.findOne(id1);
        assertThat(vote).isNotNull();
        assertThat(vote.getUserId()).isEqualTo(USER_ID_1);
        assertThat(vote.getRestaurantId()).isEqualTo(RESTAURANT_ID_1);
        assertThat(vote.getTime()).isEqualTo(TIME_TODAY);

        List<Vote> votes = repository.findAll();
        assertThat(votes).hasSize(4);
        assertThat(votes).extracting("userId", "restaurantId", "time")
                         .contains(tuple(USER_ID_1, RESTAURANT_ID_1, TIME_TODAY),
                                   tuple(USER_ID_1, RESTAURANT_ID_2, TIME_YESTERDAY),
                                   tuple(USER_ID_2, RESTAURANT_ID_1, TIME_YESTERDAY),
                                   tuple(USER_ID_2, RESTAURANT_ID_2, TIME_TODAY));

        List<Vote> votesA = repository.findByUserId(USER_ID_1);
        assertThat(votesA).hasSize(2);
        assertThat(votesA).extracting("userId", "restaurantId", "time")
                         .contains(tuple(USER_ID_1, RESTAURANT_ID_1, TIME_TODAY),
                                   tuple(USER_ID_1, RESTAURANT_ID_2, TIME_YESTERDAY));

        List<Vote> votesB = repository.findByRestaurantId(RESTAURANT_ID_1);
        assertThat(votesB).hasSize(2);
        assertThat(votesB).extracting("userId", "restaurantId", "time")
                          .contains(tuple(USER_ID_1, RESTAURANT_ID_1, TIME_TODAY),
                                    tuple(USER_ID_2, RESTAURANT_ID_1, TIME_YESTERDAY));

        List<Vote> votesC = repository.findByUserIdAndRestaurantId(USER_ID_1, RESTAURANT_ID_1);
        assertThat(votesC).hasSize(1);
        assertThat(votesC).extracting("userId", "restaurantId", "time")
                          .contains(tuple(USER_ID_1, RESTAURANT_ID_1, TIME_TODAY));
    }

    @After
    public void tearDown() {
        repository.deleteAll();
        log.info("--> Vote test end.");
    }
}
