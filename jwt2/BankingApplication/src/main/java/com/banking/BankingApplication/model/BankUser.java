package com.banking.BankingApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankUser {
	@Id
	private long userId;
	private String firstName;
	private String lastName;
	private String password;
	private String role;

}
