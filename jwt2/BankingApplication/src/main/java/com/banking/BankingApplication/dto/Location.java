package com.banking.BankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
	private String name;
	private String country;
	private String region;

}
