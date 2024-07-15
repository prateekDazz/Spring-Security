package com.employeeLeave.employeeLeave.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestDto {
	
	private LocalDate fromDate;
	private LocalDate toDate;
	private int employeeId;
	private String leaveApprovalStatus;
	

}
