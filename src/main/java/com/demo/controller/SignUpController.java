package com.demo.controller;

import com.demo.dto.UserSignInRequestDTO;
import com.demo.model.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("signUp")
public class SignUpController {

    @Autowired
    private UserService userService;

    @PostMapping("signUp")
    public ResponseEntity<String> signUp(@RequestBody UserSignInRequestDTO userSignInRequestDTO){

        User newUser = new User(userSignInRequestDTO);
        userService.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body("User sign up successfully!");
//        Don't forget to create a class to handle the errors
    }


//    @PostMapping("")
//    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO data) {
//        User newUser = new User(data);
//        userService.save(newUser);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
}
