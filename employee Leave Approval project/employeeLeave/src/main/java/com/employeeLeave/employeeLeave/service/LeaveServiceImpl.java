package com.employeeLeave.employeeLeave.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeLeave.employeeLeave.dto.LeaveRequestDto;
import com.employeeLeave.employeeLeave.entity.Employee;
import com.employeeLeave.employeeLeave.entity.Leave;
import com.employeeLeave.employeeLeave.repository.EmployeeRepository;
import com.employeeLeave.employeeLeave.repository.LeaveRepository;

@Service
public class LeaveServiceImpl {
	@Autowired
	public LeaveRepository leaveRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	public String applyLeave(LeaveRequestDto leaveRequestDto) throws IllegalAccessException {
		
		Employee emp =  employeeRepository.findById(leaveRequestDto.getEmployeeId()) .orElseThrow(()->new IllegalArgumentException("no Employee Found with given Id"));
		Leave leave = new Leave();
		leave.setEmployeeID(leaveRequestDto.getEmployeeId());
		leave.setFromDate(leaveRequestDto.getFromDate());
		leave.setToDate(leaveRequestDto.getToDate());
		leave.setStatus("OPEN");
//		leave.setLeaveId(leaveId);
		leave.setManagerId(emp.getManagerId());
		leaveRepository.save(leave);
		return leave.getLeaveId();
		
	}

	public List<LeaveRequestDto> fetchAllLeaveByManagerId(int managerId) {
		
	Employee emp =  employeeRepository.findById(managerId).orElseThrow(()->new IllegalArgumentException("no Employee Found with given Id"));
		;
		
		
		List<Leave>leaveList = leaveRepository.findLeavesByManagerId(managerId);
		List<LeaveRequestDto> leaveRequestDtos =  leaveList.stream().map(l->convertToLeaveDto(l)).collect(Collectors.toList());
		return leaveRequestDtos;
	}

	private LeaveRequestDto convertToLeaveDto(Leave l) {
		
		LeaveRequestDto leaveRequestDto = new LeaveRequestDto();
		leaveRequestDto.setEmployeeId(l.getEmployeeID());
		leaveRequestDto.setFromDate(l.getFromDate());
		leaveRequestDto.setToDate(l.getToDate());
		leaveRequestDto.setLeaveApprovalStatus(l.getStatus());
		return leaveRequestDto;
	}

	public String updateLeaves(int managerId, String leaveId, String approvalFlag) {
		
		Employee emp =  employeeRepository.findById(managerId).orElseThrow(()-> new IllegalArgumentException("No such employee exists "));
		if(emp.getHasManagerAccess() == null ||  emp.getHasManagerAccess().equalsIgnoreCase("N"))
		{
			throw new IllegalArgumentException("The given employee does not have manager access");
		}
		String leaveStatus  = "";
		Leave leave =  leaveRepository.findById(leaveId).orElseThrow(()-> new IllegalArgumentException("No such leave with given id exists"));
		
		if(leave.getManagerId()!=managerId)
		{
			throw new IllegalArgumentException("mere bhai tera manager ye nahi hai ");
		}
		
		
		if(approvalFlag!=null && approvalFlag.equalsIgnoreCase("Y"))
		{
			leave.setStatus("APPROVED");
			leaveStatus ="Your Request for leave has been approved";
		}
		else
		{
			leave.setStatus("Rejected");
			leaveStatus ="Your Request for leave has been Rejected";
		}
		leaveRepository.save(leave);
		
		return leaveStatus;
	}

	public String fetchLeaveStatusFromLeaveId(String leaveId) {
		
		Leave leave =  leaveRepository.findById(leaveId).orElseThrow(()->new IllegalArgumentException("Illegal Argument Exception"));
		return leave.getStatus();
	}
	
	

}
