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
//    Change to get the user id from the AuthContext and not receiving it from body request
    @PatchMapping("")
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserRequestDTO data) {
        service.updateUser(data);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
//    Update to instead of using an id it deletes the user of the authContext
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
//    Just using for tests, don't forget to remove in the final application
    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> users = service.getAll().stream().map(UserResponseDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
//    Just using for tests, don't forget to remove in the final application
    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserResponseDTO>> findById(@PathVariable Long id) {
        Optional<UserResponseDTO> response = service.findById(id).map(UserResponseDTO::new);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
//    Just using for tests, don't forget to remove in the final application
//    @GetMapping("/deleteAll")
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
//    Change to get the user id from the AuthContext and not receiving it from body request
//    Remember to create a TransferResponseDTO to show the transfer
    @PostMapping("/transferInternally")
    public ResponseEntity<?> transferMoneyToOtherWallet(@RequestBody InternTransferRequestDTO interTransferDTO) {

        try {
            service.selfWalletTransfer(interTransferDTO);
            return ResponseEntity.ok("Transfer successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
