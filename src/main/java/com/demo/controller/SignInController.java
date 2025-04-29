package com.demo.controller;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("signIn")
public class SignInController {

    @Autowired
    private UserService userService;

//    @Autowired
//    AuthenticationManager authenticationManager;

    private final AuthenticationManager authenticationManager;

    public SignInController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody LoginRequest loginRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
        // ...

        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse.getName());
    }

    public record LoginRequest(String username, String password) {
    }

}
