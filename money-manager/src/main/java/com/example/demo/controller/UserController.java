package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.worker.UserWorker;



@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserWorker worker;

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(worker.save(user));
    }
    
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(worker.getAll());
    }
    
}
