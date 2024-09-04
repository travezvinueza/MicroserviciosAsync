package com.ricardotravez.cuentamovimientos.service;

import com.ricardotravez.cuentamovimientos.dto.MotionDTO;

import java.util.List;

public interface MotionService {
    MotionDTO create(MotionDTO motionDTO);
    List<MotionDTO> list();
    MotionDTO getById(Long id);
    MotionDTO update(MotionDTO motionDTO);
    void deleteById(Long id);

    List<MotionDTO> findByIdClient(String idClient);

}
