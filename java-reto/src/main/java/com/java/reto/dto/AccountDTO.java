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
public class AccountDTO {
	private Long id;
	@NotBlank(message = "Account number is required")
	private String accountNumber;

	@NotBlank(message = "Type is required")
	private String type;

	@NotNull(message = "Initial balance is required")
	private BigDecimal initialBalance;

	@NotNull(message = "Status is required")
	private Boolean status;

	@NotNull(message = "Client ID is required")
	private Long clientId;

	private String clientName;
	
}