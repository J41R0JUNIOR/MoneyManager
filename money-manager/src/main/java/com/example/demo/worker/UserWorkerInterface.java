package com.example.demo.worker;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.User;

public interface UserWorkerInterface {
    User save(User user);
    public void delete(Long id);
    public List<User> getAll();
    public Optional<User> findById(Long id);
}
