package com.employeeLeave.employeeLeave.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employeeLeave.employeeLeave.entity.Leave;
@Repository
public interface LeaveRepository extends JpaRepository<Leave, String> {
	@Query("SELECT l FROM Leave l WHERE l.managerId = :managerId")
	List<Leave>findLeavesByManagerId(@Param("managerId") int managerId);

}
