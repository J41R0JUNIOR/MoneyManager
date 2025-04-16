package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Investment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	private String type;
	private Double amount;
	private Double appliedAmount;
}
