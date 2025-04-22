package com.demo.service;

import java.util.List;
import java.util.Optional;

import com.demo.dto.InternTransferRequestDTO;
import com.demo.model.User;

public interface UserServiceInterface {
    User save(User user);
    public void delete(Long id);
    public List<User> getAll();
    public Optional<User> findById(Long id);
    public Optional<User> selfWalletTransfer(InternTransferRequestDTO transferDTO);
}
