package com.demo.controller;

import com.demo.dto.UserRequestDTO;
import com.demo.dto.UserResponseDTO;
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

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO data) {
        User newUser = new User(data);
        userService.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
