package com.employeeLeave.employeeLeave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class EmployeeLeaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeLeaveApplication.class, args);
	}

}
