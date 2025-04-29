package com.demo.model;

import java.util.List;

import com.demo.dto.UserRequestDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String name;

	@Column(unique = true, nullable = false)
	private String email;
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Wallet> wallets;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Investment> investments;

	public User(UserRequestDTO data) {
		this.name = data.name();
		this.email = data.email();
		this.password = data.password();
		this.wallets = data.wallets();
		this.investments = data.investments();
	}
}
