package com.demo.controller;

import java.util.List;
import java.util.Optional;

import com.demo.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.model.User;
import com.demo.service.UserServiceImpl;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserServiceImpl service;


    
    @PatchMapping("")
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserRequestDTO data) {
        service.updateUser(data);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> users = service.getAll().stream().map(UserResponseDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserResponseDTO>> findById(@PathVariable Long id) {
        Optional<UserResponseDTO> response = service.findById(id).map(UserResponseDTO::new);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
