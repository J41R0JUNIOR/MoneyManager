package com.demo.controller;

import java.util.List;
import java.util.Optional;

import com.demo.dto.InternTransferRequestDTO;
import com.demo.dto.UserRequestDTO;
import com.demo.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.User;
import com.demo.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;



    @PostMapping("")
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO data) {
        User newUser = new User(data);
        UserResponseDTO response = new UserResponseDTO(service.save(newUser));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("")
    public ResponseEntity<UserResponseDTO> update(@RequestBody UserRequestDTO data) {
        User newUser = new User(data);
        UserResponseDTO response = new UserResponseDTO(service.save(newUser));
        return ResponseEntity.status(HttpStatus.OK).body(response);
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

    @GetMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/transferInternally")
    public ResponseEntity<UserResponseDTO> transferMoneyToOtherWallet(@RequestBody InternTransferRequestDTO interTransferDTO){
        UserResponseDTO response = new UserResponseDTO(service.selfWalletTransfer(interTransferDTO));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
