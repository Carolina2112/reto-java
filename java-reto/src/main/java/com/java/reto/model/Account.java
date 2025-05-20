package com.java.reto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")

public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "account_number", nullable = false, unique = true)
	private String accountNumber;

	@Column(nullable = false)
	private String type;

	@Column(name = "initial_balance", nullable = false)
	private BigDecimal initialBalance;

	@Column(nullable = false)
	private Boolean status;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Transaction> transactions;

}