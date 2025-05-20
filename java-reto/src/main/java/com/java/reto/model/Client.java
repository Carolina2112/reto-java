package com.java.reto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "id")


public class Client extends Person {
	
	@Column(name = "client_id", nullable = false, unique = true)
	private String clientId;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Boolean status;

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	private List<Account> accounts;
	
}