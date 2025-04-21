package com.demo.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.demo.model.User;

import jakarta.persistence.EntityManager;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;
    
    @Test 
    @DisplayName("Shoyld get User successfully from DB")
    void findByEmailSuccess() {
        String email = "user@gmail.com";
        User data = new User();
    }

    private User creatUser(User user){
        User newUser = user;
        this.entityManager.persist(newUser);
        return newUser;
    }
}
