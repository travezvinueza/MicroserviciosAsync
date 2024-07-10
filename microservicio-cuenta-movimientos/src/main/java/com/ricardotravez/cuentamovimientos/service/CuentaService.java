package com.ricardotravez.cuentamovimientos.service;

import com.ricardotravez.cuentamovimientos.dto.CuentaDTO;
import com.ricardotravez.cuentamovimientos.dto.CuentaReporteDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CuentaService {

    CuentaDTO crear(CuentaDTO cuentaDTO);
    List<CuentaDTO> listar();
    CuentaDTO obtenerPorId(Long id);
    CuentaDTO actualizar(CuentaDTO cuentaDTO);
    void eliminarPorId(Long id);
    List<CuentaDTO> findByIdCliente(String idCliente);


    List<CuentaReporteDTO> obtenerReporteCuentaCliente(Long idCliente, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
