package com.example.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//provides custom handling for authentication related errors
//This class will get called whenever there is any unauthorized request or wrong credentials is being sent through request
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
	
	private static Logger logger  = LoggerFactory.getLogger(AuthEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("authorization error {}"+authException.getMessage());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		Map<String,Object>map= new HashMap();
		map.put("status",HttpServletResponse.SC_UNAUTHORIZED);
		map.put("error","Unauthorized");
		map.put("message",authException.getMessage());
		map.put("path",request.getServletPath());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), map);
		
	}
	
	

}
