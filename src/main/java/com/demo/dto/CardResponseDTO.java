package com.demo.dto;

import com.demo.model.Card;

public record CardResponseDTO(Long id, String type, Float maxLimit, Float amount, String closeDay) {
	public CardResponseDTO(Card card) {
		this(card.getId(), card.getType(), card.getMaxLimit(), card.getAmount(), card.getCloseDay());
	}
}
