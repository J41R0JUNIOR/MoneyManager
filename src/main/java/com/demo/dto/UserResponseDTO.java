package com.demo.dto;

import com.demo.model.Card;
import com.demo.model.Investment;
import com.demo.model.User;
import com.demo.model.Wallet;

import java.util.List;

//public record UserResponseDTO(String name, String email, String password, List<Wallet> wallets, List<Investment> investments) {
//	public UserResponseDTO(User user){
//		this(user.getName(), user.getEmail(), user.getPassword(), user.getWallets(), user.getInvestments());
//	}
//}

public record UserResponseDTO(
		Long id,
		String name,
		String email,
		String password,
		List<WalletResponseDTO> wallets,
		List<InvestmentResponseDTO> investments
) {
	public UserResponseDTO(User user) {
		this(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getPassword(),
				user.getWallets() == null ? List.of() :
						user.getWallets().stream().map(WalletResponseDTO::new).toList(),
				user.getInvestments() == null ? List.of() :
						user.getInvestments().stream().map(InvestmentResponseDTO::new).toList()
		);
	}
}


