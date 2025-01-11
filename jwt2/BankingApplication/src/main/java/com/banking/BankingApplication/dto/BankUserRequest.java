package com.banking.BankingApplication.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class BankUserRequest {
	@NotNull
	@Size(min = 5,message = "Name should be of 5 letters minimum")
	private String firstName;
	private String lastName;
	private long userId;
	@NotNull
	@Size(min = 3,message = "Password should be of 3 letters minimum")

	private String password;
	private String role;
}
