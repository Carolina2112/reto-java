package com.java.reto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
	private Long id;
	private LocalDate date;
	private String transactionType;
	private BigDecimal amount;
	private BigDecimal balance;
	private Long accountId;
	private String accountNumber;
	private String clientName;
}