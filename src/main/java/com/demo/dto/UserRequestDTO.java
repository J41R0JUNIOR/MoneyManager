package com.demo.dto;

import com.demo.model.Investment;
import com.demo.model.Wallet;

import java.util.List;

public record UserRequestDTO(String name, String email, String password, List<Wallet> wallets, List<Investment> investments)  { }
