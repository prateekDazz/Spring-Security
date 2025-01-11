package com.banking.BankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CitizenDtoResponse {

	private String name;
	private long accountNumber;
	private String branchAddress;
	private long balance;

}
