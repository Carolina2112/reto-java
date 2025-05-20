package com.java.reto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")

public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDate date;

	@Column(name = "transaction_type", nullable = false)
	private String transactionType;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false)
	private BigDecimal balance;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
}