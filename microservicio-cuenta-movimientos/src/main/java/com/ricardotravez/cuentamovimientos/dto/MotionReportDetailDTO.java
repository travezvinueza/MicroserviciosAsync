package com.ricardotravez.cuentamovimientos.dto;

import com.ricardotravez.cuentamovimientos.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotionReportDetailDTO {
    private LocalDate date;
    private TransactionType transactionType;
    private double valor;
    private double saldo;
    private String accountNumber;
}
