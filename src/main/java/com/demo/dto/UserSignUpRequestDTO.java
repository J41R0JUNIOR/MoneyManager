package com.demo.dto;

import com.demo.model.RoleName;

public record UserSignUpRequestDTO(String username, String password, RoleName role) {
}
