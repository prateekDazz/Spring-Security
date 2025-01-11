package com.banking.BankingApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.BankingApplication.model.BankUser;
@Repository
public interface BankUserRepository  extends JpaRepository<BankUser, Long>{

	
	Optional<BankUser>findByfirstName(String firstName);
}
