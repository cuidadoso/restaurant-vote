package com.javaops.restaurant;

import com.javaops.restaurant.models.User;
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
        User user1 = User.builder()
                         .name("UserName1")
                         .email("user1@mail")
                         .password("pass1")
                         .build();
        User user2 = User.builder()
                         .name("UserName2")
                         .email("user2@mail")
                         .password("pass2")
                         .build();
        assertThat(user1.getId()).isNull();
        assertThat(user2.getId()).isNull();
        id1 = repository.save(user1)
                        .getId();
        log.info("--> User has created: " + user1);
        id2 = repository.save(user2)
                        .getId();
        log.info("--> User has created: " + user2);
        assertThat(repository.findById(id1)).isNotNull();
        assertThat(repository.findById(id2)).isNotNull();
    }

    @Test
    public void testDataUpdate() {
        List<User> users = repository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).extracting("name", "email", "password")
                          .contains(tuple("UserName1", "user1@mail", "pass1"),
                                    tuple("UserName2", "user2@mail", "pass2"));

        User userA = repository.findByEmail("user1@mail");
        assertThat(userA.getName()).isEqualTo("UserName1");
        userA.setName("UserName11");
        repository.save(userA);

        User userB = repository.findById(id1);
        assertThat(userB).isNotNull();
        assertThat(userB.getName()).as("check %s's name", userB.getName())
                                   .isEqualTo("UserName11");
    }

    @After
    public void tearDown() {
        repository.deleteAll();
        log.info("--> User test end.");
    }
}
