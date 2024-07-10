package com.ricardotravez.cuentamovimientos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class CuentaDTO {
    private Long id;
    private String numeroCuenta;
    private String tipoCuenta;
    private LocalDate fecha;
    private double saldoInicial;
    private boolean estado;
    private String idCliente;
    private List<MovimientoDTO> movimientos;
}
