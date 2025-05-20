package com.java.reto.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
	private Long id;
	@NotBlank(message = "Identification is required")
	private String identification;

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Gender is required")
	private String gender;

	@NotNull(message = "Age is required")
	@Positive(message = "Age must be positive")
	private Integer age;

	@NotBlank(message = "Address is required")
	private String address;

	@NotBlank(message = "Phone is required")
	private String phone;

	@NotBlank(message = "Client ID is required")
	private String clientId;

	@NotBlank(message = "Password is required")
	private String password;

	@NotNull(message = "Status is required")
	private Boolean status;


}
