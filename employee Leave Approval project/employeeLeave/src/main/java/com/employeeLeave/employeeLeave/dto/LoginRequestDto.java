package com.employeeLeave.employeeLeave.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Component
public class LoginRequestDto {
	
	public String userName;
	public String password;

}
