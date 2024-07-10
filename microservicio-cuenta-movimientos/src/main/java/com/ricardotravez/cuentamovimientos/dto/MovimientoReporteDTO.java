package com.ricardotravez.cuentamovimientos.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoReporteDTO {
    private String numeroCuenta;
    private String tipoMovimiento;
    private LocalDate fecha;
    private double valor;
    private double saldo;
    private ClienteDTO clienteDTO;
    private List<MovimientoReporteDetalleDTO> movimientoReporteDetalle;
}
