package com.employeeLeave.employeeLeave.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeeLeave.employeeLeave.dto.LeaveRequestDto;
import com.employeeLeave.employeeLeave.service.LeaveServiceImpl;

@RestController
@RequestMapping("/employee")
public class LeaveController {
	
	@Autowired
	private LeaveServiceImpl leaveService;
	
	private static Logger logger  =  LoggerFactory.getLogger(LeaveController.class);
	@PostMapping("/applyLeave")
	public ResponseEntity<String>applyLeave( @RequestBody LeaveRequestDto leaveRequestDto) throws IllegalAccessException
	{
		logger.info("inside applyLeave method with RequestDTo as "+leaveRequestDto.toString());
		
		String leaveId = leaveService.applyLeave(leaveRequestDto);
		return ResponseEntity.ok("leave Applied Successfully..Your Leave Id is"+leaveId);
	}
	
	
	@GetMapping("/fetchAllLeaves")
    @PreAuthorize("hasRole('MANAGER')")

	public ResponseEntity<List<LeaveRequestDto>>fetchAllLeave(@RequestParam("managerId")int managerId) throws Exception
	{
		logger.info("inside fetchAllLeave method");
		
		List<LeaveRequestDto> leaveRequestDtos =  leaveService.fetchAllLeaveByManagerId(managerId);
		
		return ResponseEntity.ok(leaveRequestDtos);
	}
	
	@PostMapping("/approveLeave")
    @PreAuthorize("hasRole('MANAGER')")

	public ResponseEntity<String> updateLeave(@RequestParam("managerId")int managerId,@RequestParam("leaveId")String leaveId,@RequestParam("approvalFlag")String approvalFlag)
	{
		String leaveStatus  =  leaveService.updateLeaves(managerId,leaveId,approvalFlag);
		return ResponseEntity.ok(leaveStatus);
	}

	
	@GetMapping("/fetchLeaveStatus")
	public ResponseEntity<String>fetchLeaveStatus(@RequestParam("leaveId")String leaveId)
	{
		String leaveStatus  =  leaveService.fetchLeaveStatusFromLeaveId(leaveId);
		return ResponseEntity.ok(leaveStatus);
	}
}
