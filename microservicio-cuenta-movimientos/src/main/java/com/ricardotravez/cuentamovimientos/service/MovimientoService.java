package com.ricardotravez.cuentamovimientos.service;

import com.ricardotravez.cuentamovimientos.dto.CuentaDTO;
import com.ricardotravez.cuentamovimientos.dto.CuentaReporteDTO;
import com.ricardotravez.cuentamovimientos.dto.MovimientoDTO;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoService {
    MovimientoDTO crear(MovimientoDTO movimientoDTO);
    List<MovimientoDTO> listar();
    MovimientoDTO obtenerPorId(Long id);
    MovimientoDTO actualizar(MovimientoDTO movimientoDTO);
    void eliminarPorId(Long id);

    List<MovimientoDTO> findByIdCliente(String idCliente);

}
