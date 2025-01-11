package com.banking.BankingApplication.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Current {
	
	@JsonProperty("observation_time")
	private String observationTime;
	private int temperature;
	@JsonProperty("weather_descriptions")
	private List<String> weatherDescription;
	
	
	

}
