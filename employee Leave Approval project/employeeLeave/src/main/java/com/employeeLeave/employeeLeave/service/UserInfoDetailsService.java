package com.employeeLeave.employeeLeave.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.employeeLeave.employeeLeave.entity.Employee;
import com.employeeLeave.employeeLeave.repository.EmployeeRepository;
@Component
public class UserInfoDetailsService implements UserDetailsService {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<Employee>employee = employeeRepository.findByfirstName(username);
		
		if(employee.isPresent())
		{
			if(employee.get().getHasManagerAccess()!=null && employee.get().getHasManagerAccess().equalsIgnoreCase("Y"))
			{
				return new User(username, employee.get().getPassword(),List.of(new SimpleGrantedAuthority("ROLE_MANAGER")));
			}
			return new User(username, employee.get().getPassword(),new ArrayList());
		}
        throw new UsernameNotFoundException("User not found");

	}

	

}
