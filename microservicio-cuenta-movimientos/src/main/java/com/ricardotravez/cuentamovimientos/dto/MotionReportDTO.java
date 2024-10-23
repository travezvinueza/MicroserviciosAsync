package com.ricardotravez.cuentamovimientos.dto;

import com.ricardotravez.cuentamovimientos.enums.TransactionType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotionReportDTO {
    private String accountNumber;
    private TransactionType transactionType;
    private LocalDate date;
    private double valor;
    private double saldo;
    private ClientDTO clientDTO;
    private List<MotionReportDetailDTO> motionReportDetail;
}
