package com.banking.BankingApplication.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int citizenId;
    private String firstName;
    private String lastName;
    @NotNull
    @Column(unique = true)
    private long accountNumber;
    private String branchAddress;
    private long balance;

}
