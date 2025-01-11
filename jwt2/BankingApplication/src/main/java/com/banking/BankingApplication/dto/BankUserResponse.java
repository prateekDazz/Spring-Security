package com.banking.BankingApplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankUserResponse {
	private String firstName;
	@JsonIgnore
	private String lastName;
	private String role;
	private String jwtToken;

}
