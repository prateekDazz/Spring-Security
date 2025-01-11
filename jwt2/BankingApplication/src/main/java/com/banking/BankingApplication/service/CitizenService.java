package com.banking.BankingApplication.service;

import com.banking.BankingApplication.dto.BankUserRequest;
import com.banking.BankingApplication.dto.CitizenDtoRequest;
import com.banking.BankingApplication.dto.CitizenDtoResponse;
import com.banking.BankingApplication.model.Citizen;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CitizenService {

    public CitizenDtoResponse createCitizen(CitizenDtoRequest citizenDtoRequest);
    public List<CitizenDtoResponse>fetchAllCitizen();
    public String createBankAdmin(BankUserRequest bankUserRequest);
    public CitizenDtoResponse fetchAccountDetails(long accountNumber);
    public Citizen findByAccountNumber(long accountNo);
	public CitizenDtoResponse saveCitizen(Citizen citizen);

}
