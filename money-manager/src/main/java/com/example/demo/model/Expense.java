package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Expense {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "card_id")
	@JsonBackReference
	private Card card;

	private String name;

	@OneToOne(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
	private Installment installment;
}
