package com.ricardotravez.cuentamovimientos.dto;

import com.ricardotravez.cuentamovimientos.enums.TransactionType;
import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotionDTO {
    private Long id;
    private LocalDateTime date;
    private TransactionType transactionType;
    private double valor;
    private double saldo;
    private String accountNumber;
    private String idClient;
}
