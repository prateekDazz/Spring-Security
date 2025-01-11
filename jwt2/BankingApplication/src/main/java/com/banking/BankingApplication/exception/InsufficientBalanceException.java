package com.banking.BankingApplication.exception;

public class InsufficientBalanceException extends RuntimeException {
	
	public InsufficientBalanceException(String msg) {
		super(msg);
	}

}
