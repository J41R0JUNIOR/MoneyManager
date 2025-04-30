package com.demo.controller;

import com.demo.dto.UserRequestDTO;
import com.demo.dto.UserResponseDTO;
import com.demo.dto.UserSignInRequestDTO;
import com.demo.model.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private UserService userService;
	private final AuthenticationManager authenticationManager;

	public AuthController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/signIn")
	public ResponseEntity<String> signIn(@RequestBody LoginRequest loginRequest) {
		Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
		Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

		return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse.getName());
	}

	public record LoginRequest(String username, String password) {
	}

	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody UserSignInRequestDTO userSignInRequestDTO){

		User newUser = new User(userSignInRequestDTO);
		userService.save(newUser);

		return ResponseEntity.status(HttpStatus.OK).body("User sign up successfully!");
//        Don't forget to create a class to handle the errors
	}

	@PostMapping("")
	public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO data) {
		User newUser = new User(data);
		userService.save(newUser);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
