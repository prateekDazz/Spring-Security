package com.example.security.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.security.dto.LoginRequest;
import com.example.security.dto.LoginResponse;
import com.example.security.jwt.JwtUtils;

@RestController
public class LoginController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private Logger logger  = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private JwtUtils jwtUtils;
	@PostMapping("/signIn")
	public ResponseEntity<?>authenticateUser(@RequestBody LoginRequest loginRequest)
	{
		logger.info("Login Controller inside authenticateUser() Method");
		System.out.println("hello in sign in method");
		Authentication authentication;
		try
		{
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
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String jwtToken =  jwtUtils.generateTokenFromUserName(userDetails);
		
		List<String>roles = userDetails.getAuthorities().stream().map(authorities->authorities.getAuthority()).toList();
	
	LoginResponse loginResponse =  new LoginResponse();
	loginResponse.setJwtToken(jwtToken);
	loginResponse.setUserName(userDetails.getUsername());
	loginResponse.setRoles(roles);
	return ResponseEntity.ok(loginResponse);
	}

}
