package com.demo.dto;

import com.demo.model.Investment;

public record InvestmentResponseDTO(Long id, String type, Double amount, Double appliedAmount) {
	public InvestmentResponseDTO(Investment investment) {
		this(investment.getId(), investment.getType(), investment.getAmount(), investment.getAppliedAmount());
	}
}
