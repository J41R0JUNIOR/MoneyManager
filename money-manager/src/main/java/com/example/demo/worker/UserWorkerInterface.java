package com.example.demo.worker;

import java.util.List;

import com.example.demo.model.User;

public interface UserWorkerInterface {
    User save(User user);
    public List<User> getAll();
}
