package com.demo.dto;

import com.demo.model.Role;
import java.util.List;

public record RecoveryUserRequestDTO(Long id, String email, List<Role> roles) {
}
