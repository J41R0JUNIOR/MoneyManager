package com.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.demo.dto.UserRequestDTO;
import com.demo.dto.UserSignInRequestDTO;
import com.demo.dto.UserSignUpRequestDTO;
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

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	private String name;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Wallet> wallets;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Investment> investments;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_roles", inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;


	public User(UserRequestDTO data) {
		this.name = data.name();
		this.email = data.email();
		this.password = data.password();
		this.wallets = data.wallets();
		this.investments = data.investments();
	}

	public User(UserSignUpRequestDTO data){
		this.email = data.username();
		this.name = null;
		this.wallets = new ArrayList<>();
		this.investments = new ArrayList<>();
		this.roles = new ArrayList<>();
	}
}
