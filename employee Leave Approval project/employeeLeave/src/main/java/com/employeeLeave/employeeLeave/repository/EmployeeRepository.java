package com.employeeLeave.employeeLeave.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employeeLeave.employeeLeave.entity.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	Optional<Employee>findByManagerId(int managerId);

Optional<Employee>findByfirstName(String firstName);
}
