package com.demo.dto;

import com.demo.model.Wallet;

import java.util.List;

public record WalletResponseDTO(Long id, String name, List<CardResponseDTO> cards) {
	public WalletResponseDTO(Wallet wallet) {
		this(
				wallet.getId(),
				wallet.getName(),
				wallet.getCards() == null ? List.of() :
						wallet.getCards().stream().map(CardResponseDTO::new).toList()
		);
	}
}
