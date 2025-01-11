package com.banking.BankingApplication.repository;

import com.banking.BankingApplication.model.Citizen;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen,Integer> {
	
	Optional<Citizen>findByAccountNumber(long accountNumber);


}
