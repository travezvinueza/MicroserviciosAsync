package com.ricardotravez.clientepersona.dto;

import com.ricardotravez.clientepersona.dto.enums.AccountType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String accountNumber;
    private AccountType accountType;
    private double initialBalance;
    private boolean state;
    private List<Motion> motions;
}
