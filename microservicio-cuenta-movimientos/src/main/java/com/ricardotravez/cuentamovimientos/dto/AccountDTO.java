package com.ricardotravez.cuentamovimientos.dto;

import com.ricardotravez.cuentamovimientos.enums.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private LocalDateTime date;
    private double initialBalance;
    private boolean state;
    private String idClient;
    private List<MotionDTO> motions;
}
