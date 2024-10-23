package com.ricardotravez.clientepersona.dto;

import com.ricardotravez.clientepersona.dto.enums.TransactionType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Motion {
    private TransactionType transactionType;
    private double valor;
    private double saldo;
    private String accountNumber;
}
