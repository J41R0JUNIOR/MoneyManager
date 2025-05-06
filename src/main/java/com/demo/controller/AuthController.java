package com.demo.controller;

import com.demo.dto.*;
import com.demo.model.User;
import com.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@PostMapping("/signIn")
	public ResponseEntity<RecoveryJwtTokenRequestDTO> signIn(@RequestBody UserSignInRequestDTO userDTO) {
		RecoveryJwtTokenRequestDTO token = userServiceImpl.authenticateUser(userDTO);
		return new ResponseEntity<>(token, HttpStatus.OK);
	}

	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody UserSignUpRequestDTO userDTO){
		userServiceImpl.createUser(userDTO);
		return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
	}

	@GetMapping("/test/user")
	public ResponseEntity<String> testUser(){
		return new ResponseEntity<>("User authenticated successfully", HttpStatus.OK);
	}

	@GetMapping("/test/adm")
	public ResponseEntity<String> testAdm(){
		return new ResponseEntity<>("Adm authenticated successfully", HttpStatus.OK);
	}
}
