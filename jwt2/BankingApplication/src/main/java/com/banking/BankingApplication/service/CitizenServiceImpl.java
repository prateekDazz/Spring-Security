package com.banking.BankingApplication.service;

import com.banking.BankingApplication.dto.BankUserRequest;
import com.banking.BankingApplication.dto.CitizenDtoRequest;
import com.banking.BankingApplication.dto.CitizenDtoResponse;
import com.banking.BankingApplication.exception.AccountNotExistsException;
import com.banking.BankingApplication.exception.AdminNotFoundException;
import com.banking.BankingApplication.model.BankUser;
import com.banking.BankingApplication.model.Citizen;
import com.banking.BankingApplication.repository.BankUserRepository;
import com.banking.BankingApplication.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class CitizenServiceImpl  implements CitizenService{
    @Autowired
    private CitizenRepository citizenRepository;
    
    @Autowired
    private BankUserRepository bankUserRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;



    @Override
    public CitizenDtoResponse createCitizen(CitizenDtoRequest citizenDtoRequest) {
    	 UUID uuid = UUID.randomUUID();
		Citizen citizen = new Citizen();
		long randomPositiveLong = Math.abs(uuid.getMostSignificantBits());
		citizen.setFirstName(citizenDtoRequest.getFirstName());
		citizen.setLastName(citizenDtoRequest.getLastName());
		citizen.setBalance(0L);
		citizen.setAccountNumber(randomPositiveLong);
		citizen.setBranchAddress(citizenDtoRequest.getBranchAddress());
		citizenRepository.save(citizen);
		CitizenDtoResponse citizenDtoResponse = new CitizenDtoResponse();
		citizenDtoResponse.setBalance(citizenDtoRequest.getBalance());
		
	        
	        // Extract the most significant bits and ensure the value is positive
	        
		citizenDtoResponse.setAccountNumber(randomPositiveLong);
		citizenDtoResponse.setBalance(citizen.getBalance());
		citizenDtoResponse.setBranchAddress(citizen.getBranchAddress());
		citizenDtoResponse.setName(citizen.getFirstName());
		BankUser bankUser = new BankUser();
		bankUser.setFirstName(citizenDtoRequest.getFirstName());
		bankUser.setLastName(citizenDtoRequest.getLastName());
		bankUser.setPassword(bCryptPasswordEncoder.encode(citizenDtoRequest.getPassword()));
		bankUser.setRole("CITIZEN");
        long bankId = Math.abs(uuid.getMostSignificantBits());
        bankUser.setUserId(bankId);
        bankUserRepository.save(bankUser);
		return citizenDtoResponse;
    }

    @Override
    public List<CitizenDtoResponse> fetchAllCitizen() {
        List<Citizen>citizenList =  citizenRepository.findAll();
        List<CitizenDtoResponse>citizenDtoResponseList  =  citizenList.stream().map(citizen -> new CitizenDtoResponse(citizen.getFirstName(),citizen.getAccountNumber(),citizen.getBranchAddress(),citizen.getBalance())).collect(Collectors.toList());
        return citizenDtoResponseList;
    }

   

	@Override
	public String createBankAdmin(BankUserRequest bankUserRequest) {
		BankUser bankUser = new BankUser();
		bankUser.setFirstName(bankUserRequest.getFirstName());
		bankUser.setLastName(bankUserRequest.getLastName());
		bankUser.setPassword(bCryptPasswordEncoder.encode(bankUserRequest.getPassword()));
		bankUser.setRole(bankUserRequest.getRole());
		UUID uuid = UUID.randomUUID();
        long bankId = Math.abs(uuid.getMostSignificantBits());
        bankUser.setUserId(bankId);
        bankUserRepository.save(bankUser);
		return("Admin created Successfully");
	}

	@Override
	public CitizenDtoResponse fetchAccountDetails(long accountNumber) {
		Optional<Citizen> citizen =  citizenRepository.findByAccountNumber(accountNumber);
		if(citizen.isEmpty())
		{
			return null;
		}
		Citizen cit1 =  citizen.get();
		CitizenDtoResponse citizenDtoResponse =  new CitizenDtoResponse(cit1.getFirstName(),cit1.getAccountNumber(),cit1.getBranchAddress(),cit1.getBalance());
		return citizenDtoResponse;
	}

	@Override
	public Citizen findByAccountNumber(long accountNo) {
		
Citizen citizen =  citizenRepository.findByAccountNumber(accountNo).orElseThrow(()->new AccountNotExistsException("account with the given no::"+accountNo+"does not exists"));;		
	return citizen;
	}

	@Override
	public CitizenDtoResponse saveCitizen(Citizen citizen) {
		// Citi
		
		Citizen savedCitizen =  citizenRepository.save(citizen);
		CitizenDtoResponse citizenDtoResponse = new CitizenDtoResponse();
		citizenDtoResponse.setAccountNumber(citizen.getAccountNumber());
		citizenDtoResponse.setBalance(citizen.getBalance());
		citizenDtoResponse.setName(citizen.getFirstName());
		citizenDtoResponse.setBranchAddress(citizen.getBranchAddress());
		
		return citizenDtoResponse;
	}
	
}
