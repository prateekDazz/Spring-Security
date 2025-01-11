package com.banking.BankingApplication.mapper;

import com.banking.BankingApplication.dto.CitizenDtoRequest;
import com.banking.BankingApplication.dto.CitizenDtoResponse;

public class Mapper {
	
	public static CitizenDtoResponse mapToResponse(CitizenDtoRequest citizenDtoRequest)
	{
		CitizenDtoResponse citizenDtoResponse =  new CitizenDtoResponse();
		citizenDtoResponse.setBalance(citizenDtoRequest.getBalance());
		citizenDtoResponse.setBranchAddress(citizenDtoRequest.getBranchAddress());
		citizenDtoResponse.setName(citizenDtoRequest.getFirstName());
		return citizenDtoResponse;
	}
	
	public static CitizenDtoRequest mapToRequest(CitizenDtoResponse citizenDtoResponse)
	{
		CitizenDtoRequest citizenDtoRequest = new CitizenDtoRequest();
		citizenDtoRequest.setBalance(citizenDtoResponse.getBalance());
		citizenDtoRequest.setBranchAddress(citizenDtoResponse.getBranchAddress());
		citizenDtoRequest.setFirstName(citizenDtoResponse.getName());
		return citizenDtoRequest;
	}
	

}
