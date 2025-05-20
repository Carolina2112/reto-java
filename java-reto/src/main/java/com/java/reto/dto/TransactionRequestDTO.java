package com.java.reto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {
	@NotBlank(message = "Account number is required")
	private String accountNumber;
	@NotNull(message = "Amount is required")
	private BigDecimal amount;
}