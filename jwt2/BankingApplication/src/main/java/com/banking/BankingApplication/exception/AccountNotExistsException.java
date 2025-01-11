package com.banking.BankingApplication.exception;

public class AccountNotExistsException extends RuntimeException {
	
	public AccountNotExistsException(String msg)
	{
		super(msg);
	}

}
