package com.example.security.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	private String jwtToken;
	private String userName;
	private List<String>roles;

}
