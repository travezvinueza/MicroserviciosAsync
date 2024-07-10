package com.ricardotravez.cuentamovimientos.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDTO {
    private Long id;
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private double valor;
    private double saldo;
    private String numeroCuenta;
    private String idCliente;
}
