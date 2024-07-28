package com.ricardotravez.cuentamovimientos.service;

import com.ricardotravez.cuentamovimientos.dto.MotionDTO;

import java.util.List;

public interface MotionService {
    MotionDTO crear(MotionDTO motionDTO);
    List<MotionDTO> listar();
    MotionDTO obtenerPorId(Long id);
    MotionDTO actualizar(MotionDTO motionDTO);
    void eliminarPorId(Long id);

    List<MotionDTO> findByIdClient(String idClient);

}
