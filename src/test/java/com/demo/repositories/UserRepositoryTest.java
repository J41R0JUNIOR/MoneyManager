package com.demo.repositories;

import com.demo.dto.UserRequestDTO;
import com.demo.dto.UserResponseDTO;
import com.demo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.demo.model.User;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    UserRepository repository;
    @Autowired
    EntityManager entityManager;
    
    @Test 
    @DisplayName("Should get User successfully from DB")
    void findByEmailSuccess() {
        String email = "UserTest@gmail.com";
        UserRequestDTO data = new UserRequestDTO("UserTest", email, "passwordTest", new ArrayList<>(), new ArrayList<>());

        this.creatUser(data);

        Optional<User> foundedUser = this.repository.findUserByEmail(email);
        assertThat(foundedUser.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get User from DB when user not exists")
    void findByEmailError() {
        Optional<User> foundedUser = this.repository.findUserByEmail("UserTest@gmail.com");
        assertThat(foundedUser.isEmpty()).isTrue();
    }

     void creatUser(UserRequestDTO user){
        User newUser = new User(user);
        this.entityManager.persist(newUser);
    }

}