package com.employeeLeave.employeeLeave.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeeLeave.employeeLeave.dto.LoginRequestDto;
import com.employeeLeave.employeeLeave.dto.LoginResponseDto;
import com.employeeLeave.employeeLeave.jwt.JwtUtils;
import com.employeeLeave.employeeLeave.service.UserInfoDetailsService;

@RestController
@RequestMapping("/signIn")
public class LogInController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserInfoDetailsService userDetailsService;
	
	private Logger logger  = LoggerFactory.getLogger(LogInController.class);
	@Autowired
	private JwtUtils jwtUtils;
	@PostMapping
	public ResponseEntity<?>authenticateUser(@RequestBody LoginRequestDto loginRequest)
	{
		logger.info("Login Controller inside authenticateUser() Method");
		System.out.println("hello in sign in method");
		Authentication authentication;
		try
		{
			
//			inside this bl;ock user name and password is verified from DB 
			authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));
		}
		catch(Exception e)
		{
			Map<String,Object>map =  new HashMap();
			map.put("message", "Bad Credentials");
			map.put("status", false);
			return new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		 UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserName());
//		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String jwtToken =  jwtUtils.generateTokenFromUserName(userDetails);
		
		List<String>roles = userDetails.getAuthorities().stream().map(authorities->authorities.getAuthority()).toList();
	
	LoginResponseDto loginResponse =  new LoginResponseDto();
	loginResponse.setJwtToken(jwtToken);
	loginResponse.setUserName(userDetails.getUsername());
	loginResponse.setRoles(roles);
	return ResponseEntity.ok(loginResponse);
	}

}
