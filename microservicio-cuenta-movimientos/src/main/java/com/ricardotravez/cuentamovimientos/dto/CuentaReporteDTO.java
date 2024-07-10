package com.ricardotravez.cuentamovimientos.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaReporteDTO {
    private String numeroCuenta;
    private String tipoCuenta;
    private LocalDate fecha;
    private double saldoInicial;
    private boolean estado;
    private ClienteDTO cliente;
    private List<CuentaReporteDetalleDTO> cuentaReporteDetalle;
}
