package com.employeeLeave.employeeLeave.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EMPLOYEE_LEAVE")
public class Leave {
	@Id
	private String leaveId;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String status;
	private int EmployeeID;
	private int managerId;
	@PrePersist
    protected void onCreate() {
        if (this.leaveId == null) {
            this.leaveId = UUID.randomUUID().toString();
        }
    }

}
