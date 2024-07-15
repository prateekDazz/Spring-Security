package com.employeeLeave.employeeLeave.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
	private String jwtToken;
	private String userName;
	private List<String>roles;


}
