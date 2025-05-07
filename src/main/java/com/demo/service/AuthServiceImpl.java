package com.demo.service;

import com.demo.config.SecurityConfig;
import com.demo.dto.RecoveryJwtTokenRequestDTO;
import com.demo.dto.UserSignInRequestDTO;
import com.demo.dto.UserSignUpRequestDTO;
import com.demo.model.Role;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.security.JwtTokenServiceImpl;
import com.demo.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthServiceInterface {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenServiceImpl jwtTokenService;

	@Autowired
	private SecurityConfig securityConfig;

	@Autowired
	private UserRepository userRepository;



	@Override
	public RecoveryJwtTokenRequestDTO authenticateUser(UserSignInRequestDTO user){
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.username(), user.password());
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return new RecoveryJwtTokenRequestDTO(jwtTokenService.generateToken(userDetails));
	}

	@Override
	public void createUser(UserSignUpRequestDTO user){
		String encodedPassword = securityConfig.passwordEncoder().encode(user.password());

		User newUser = new User(user);
		newUser.setPassword(encodedPassword);
		newUser.setRoles(List.of(Role.builder().name(user.role()).build()));

		userRepository.save(newUser);
	}
}
