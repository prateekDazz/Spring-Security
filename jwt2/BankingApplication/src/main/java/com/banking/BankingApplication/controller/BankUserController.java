package com.banking.BankingApplication.controller;

import com.banking.BankingApplication.dto.BankUserRequest;
import com.banking.BankingApplication.dto.BankUserResponse;
import com.banking.BankingApplication.dto.CitizenDtoRequest;
import com.banking.BankingApplication.dto.CitizenDtoResponse;
import com.banking.BankingApplication.exception.AccountNotExistsException;
import com.banking.BankingApplication.exception.InsufficientBalanceException;
import com.banking.BankingApplication.exception.InvalidOperationException;
import com.banking.BankingApplication.jwt.JwtUtils;
import com.banking.BankingApplication.model.Citizen;
import com.banking.BankingApplication.service.CitizenService;
import com.banking.BankingApplication.service.UserInfoDetailsService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class BankUserController {
	@Autowired
	private CitizenService citizenService;
	private static Logger  logger  = LoggerFactory.getLogger(BankUserController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserInfoDetailsService userDetailsService;
	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/citizen")
	@PreAuthorize("hasRole('ADMIN')")
//this method is used to register the people with their account
	public ResponseEntity<CitizenDtoResponse> createCitizenUser( @Valid @RequestBody CitizenDtoRequest citizenDtoRequest) {
		CitizenDtoResponse citizenDtoResponse = citizenService.createCitizen(citizenDtoRequest);
		return ResponseEntity.ok(citizenDtoResponse);
	}

	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> createbankAdmin(@RequestBody BankUserRequest bankUserRequest) {
		String response = citizenService.createBankAdmin(bankUserRequest);
		return ResponseEntity.ok(response);

	}
	@GetMapping("/login/")
	public ResponseEntity<?>logInCitizen(@Valid @RequestBody BankUserRequest bankUserRequest)
	{
		
		logger.info("Login Controller inside authenticateUser() Method");
		System.out.println("hello in sign in method");
		Authentication authentication;
		try
		{
			
//			inside this bl;ock user name and password is verified from DB 
			authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(bankUserRequest.getFirstName(),bankUserRequest.getPassword()));
		}
		catch(Exception e)
		{
			Map<String,Object>map =  new HashMap<String, Object>();
			map.put("message", "Bad Credentials");
			map.put("status", false);
			return new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		 UserDetails userDetails = userDetailsService.loadUserByUsername(bankUserRequest.getFirstName());
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String jwtToken =  jwtUtils.generateTokenFromUserName(userDetails);
		
		List<String>roles = userDetails.getAuthorities().stream().map(authorities->authorities.getAuthority()).toList();
	BankUserResponse citizenDtoResponse = new BankUserResponse();
	citizenDtoResponse.setJwtToken(jwtToken);
	citizenDtoResponse.setFirstName(userDetails.getUsername());
	String role  = roles.stream().collect(Collectors.joining(","));
	citizenDtoResponse.setRole(role);
	return ResponseEntity.ok(citizenDtoResponse);
		
	}
	@GetMapping("/fetchAccountDetails")
	@PreAuthorize("hasAnyRole('ADMIN', 'CITIZEN')")
	public ResponseEntity<CitizenDtoResponse>getBankAccountDetails(@RequestParam("acNo")long userId)
	{
		CitizenDtoResponse bankUserResponse = citizenService.fetchAccountDetails(userId);
		if(bankUserResponse==null)
		{
			throw new AccountNotExistsException("account with acNo::"+userId+"does not exists");
		}
		return ResponseEntity.ok(bankUserResponse);
				
	}
	@PostMapping("/updateBalance")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CitizenDtoResponse>updateAmount(@RequestParam("acNo")long userId,@RequestParam("amount")long amount,@RequestParam("operation")String operation)
	{
		boolean validFlag = false;
		Citizen citizen = citizenService.findByAccountNumber(userId);
		if(operation!=null && operation.equalsIgnoreCase("DEPOSIT"))
		{
			citizen.setBalance(citizen.getBalance()+amount);
			validFlag = true;

		}
		else if(operation!=null && operation.equalsIgnoreCase("WITHDRAW"))
		{
			validFlag = true;
			if(citizen.getBalance()<amount)
				
			{
				throw new InsufficientBalanceException("Balance Amount :: Rs "+citizen.getBalance()+" is less than the amount to be withdrawn::Rs"+amount);
			}
			citizen.setBalance(citizen.getBalance()-amount);
		}
		else if(operation==null || !validFlag)
		{
			throw new InvalidOperationException("Operation ::"+operation+" is not supported ");
			
		}
		CitizenDtoResponse citizenDtoResponse =  citizenService.saveCitizen(citizen);
		return ResponseEntity.ok(citizenDtoResponse);
		
		
	}
}
