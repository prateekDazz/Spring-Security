package com.employeeLeave.employeeLeave;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		Date d=  new Date();
//		System.out.println(d.toString());
		
		String dateString = "2023-07-14"; // Example date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        System.out.println(date); 
	}

}
