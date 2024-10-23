package com.ricardotravez.cuentamovimientos.dto;


import com.ricardotravez.cuentamovimientos.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountReport {
    private String accountNumber;
    private AccountType accountType;
    private LocalDate date;
    private double initialBalance;
    private boolean state;
    private ClientDTO client;
    private List<AccountReportDetailDTO> accountReportDetail;
}
