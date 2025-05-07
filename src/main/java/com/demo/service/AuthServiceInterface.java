package com.demo.service;

import com.demo.dto.RecoveryJwtTokenRequestDTO;
import com.demo.dto.UserSignInRequestDTO;
import com.demo.dto.UserSignUpRequestDTO;

public interface AuthServiceInterface {
	void createUser(UserSignUpRequestDTO user);

	RecoveryJwtTokenRequestDTO authenticateUser(UserSignInRequestDTO user);
}
