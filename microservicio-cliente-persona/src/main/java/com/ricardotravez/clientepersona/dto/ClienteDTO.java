package com.ricardotravez.clientepersona.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO{
    private Long id;
    private String nombre;
    private String genero;
    private int edad;
    private String identificacion;
    private String direccion;
    private LocalDate fecha;
    private String telefono;
    private String contrasena;
    private boolean estado;
    private List<CuentaDTO> cuentas;
}
