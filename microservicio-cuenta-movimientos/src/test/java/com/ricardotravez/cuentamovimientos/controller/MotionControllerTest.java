package com.ricardotravez.cuentamovimientos.controller;

import com.ricardotravez.cuentamovimientos.dto.MotionDTO;
import com.ricardotravez.cuentamovimientos.service.MotionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MotionControllerTest {

    @InjectMocks
    private MotionController motionController;
    @Mock
    private MotionService motionService;

    @Test
    void create() {
        MotionDTO movimientoCreado = new MotionDTO();
        movimientoCreado.setId(1L);

        when(motionService.create(any(MotionDTO.class))).thenReturn(movimientoCreado);
        ResponseEntity<MotionDTO> responseEntity = motionController.crear(new MotionDTO());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(movimientoCreado, responseEntity.getBody());
    }

    @Test
    void list() {
        List<MotionDTO> movimientos = new ArrayList<>();

        when(motionService.list()).thenReturn(movimientos);
        ResponseEntity<List<MotionDTO>> responseEntity = motionController.listar();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(movimientos, responseEntity.getBody());
    }

    @Test
    void getById() {
        MotionDTO movimientoExistente = new MotionDTO();
        movimientoExistente.setId(1L);

        when(motionService.getById(1L)).thenReturn(movimientoExistente);
        ResponseEntity<MotionDTO> responseEntity = motionController.obtenerPorId(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(movimientoExistente, responseEntity.getBody());
    }

    @Test
    void deleteById() {
        motionController.eliminarPorId(1L);
        verify(motionService).deleteById(1L);
    }

    @Test
    void update() {
        MotionDTO movimientoActualizado = new MotionDTO();
        movimientoActualizado.setId(1L);

        when(motionService.update(any(MotionDTO.class))).thenReturn(movimientoActualizado);
        ResponseEntity<MotionDTO> responseEntity = motionController.actualizar(new MotionDTO());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(movimientoActualizado, responseEntity.getBody());
    }
}