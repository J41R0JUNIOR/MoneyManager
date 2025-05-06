package com.demo.service;

import java.util.List;
import java.util.Optional;
import com.demo.dto.InternTransferRequestDTO;
import com.demo.dto.UserRequestDTO;
import com.demo.dto.UserSignUpRequestDTO;
import com.demo.model.User;

public interface UserServiceInterface {

    void updateUser(UserRequestDTO user);

	void deleteAll();

    void delete(Long id);

    List<User> getAll();

    Optional<User> findById(Long id);

    void selfWalletTransfer(InternTransferRequestDTO transferDTO) throws Exception;
}
