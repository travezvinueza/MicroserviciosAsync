package com.ricardotravez.clientepersona.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDTO {
    private String tipoMovimiento;
    private double valor;
    private double saldo;
    private String numeroCuenta;
}
