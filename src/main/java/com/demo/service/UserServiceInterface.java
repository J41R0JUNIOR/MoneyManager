package com.demo.service;

import java.util.List;
import java.util.Optional;

import com.demo.dto.InternTransferRequestDTO;
import com.demo.model.User;

public interface UserServiceInterface {
    void save(User user);
    void deleteAll();
    void delete(Long id);
    List<User> getAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    void selfWalletTransfer(InternTransferRequestDTO transferDTO) throws Exception;
}
