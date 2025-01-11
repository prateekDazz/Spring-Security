package com.banking.BankingApplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherResponseDto {
	@JsonProperty("current")
	private Current current;
	@JsonProperty("location")
	private Location location;
	
	

}
