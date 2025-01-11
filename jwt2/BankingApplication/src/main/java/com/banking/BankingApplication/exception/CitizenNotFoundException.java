package com.banking.BankingApplication.exception;

import org.springframework.web.bind.annotation.RestController;

public class CitizenNotFoundException extends RuntimeException {

    public CitizenNotFoundException(String message)
    {
        super(message);
    }


}
