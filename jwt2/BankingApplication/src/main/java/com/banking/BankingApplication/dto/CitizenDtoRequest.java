package com.banking.BankingApplication.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDtoRequest {
@NotEmpty(message = "firstName cannot be null")
@Size(min = 3, message = "firstName should be greater than or equal to 3 characters ")
    private String firstName;
private String lastName;
    private String branchAddress;
    private long balance;
    @NotEmpty(message = "password cannot be null")
    @Size(min = 3, message = "password should be greater than or equal to 3 characters ")
    private String password;

}
