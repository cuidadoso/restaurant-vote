package com.javaops.restaurant;

import com.javaops.restaurant.model.User;
import com.javaops.restaurant.repository.UserRepository;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.javaops.restaurant.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;
    private String id1;
    private String id2;

    @Before
    public void setUp() {
        log.info("--> User test start.");
        repository.deleteAll();
        User user1 = User.builder()
                         .name(NAME_1)
                         .email(EMAIL_1)
                         .password(PASSWORD_1)
                         .build();
        User user2 = User.builder()
                         .name(NAME_2)
                         .email(EMAIL_2)
                         .password(PASSWORD_2)
                         .build();
        assertThat(user1.getId()).isNull();
        assertThat(user2.getId()).isNull();
        id1 = repository.save(user1)
                        .getId();
        log.info("--> User has created: " + user1);
        id2 = repository.save(user2)
                        .getId();
        log.info("--> User has created: " + user2);
    }

    @Test
    public void testDataUpdate() {
        assertThat(repository.findOne(id1)).isNotNull();
        assertThat(repository.findOne(id2)).isNotNull();

        List<User> users = repository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).extracting("name", "email", "password")
                          .contains(tuple(NAME_1, EMAIL_1, PASSWORD_1),
                                    tuple(NAME_2, EMAIL_2, PASSWORD_2));

        User userA = repository.findByEmail(EMAIL_1);
        assertThat(userA.getName()).isEqualTo(NAME_1);

        User userB = repository.findOne(id2);
        assertThat(userB).isNotNull();
        assertThat(userB.getName()).as("check %s's name", userB.getName())
                                   .isEqualTo(NAME_2);
    }

    @After
    public void tearDown() {
        repository.deleteAll();
        log.info("--> User test end.");
    }
}
