package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Wallet {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	private String name;

	@OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Card> cards;
}
