package com.ricardotravez.cuentamovimientos.controller;

import com.ricardotravez.cuentamovimientos.dto.MovimientoDTO;
import com.ricardotravez.cuentamovimientos.service.MovimientoService;
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
class MovimientoControllerTest {
    @InjectMocks
    private MovimientoController movimientoController;
    @Mock
    private MovimientoService movimientoService;


    @Test
    void crear() {
        MovimientoDTO movimientoCreado = new MovimientoDTO();
        movimientoCreado.setId(1L);

        when(movimientoService.crear(any(MovimientoDTO.class))).thenReturn(movimientoCreado);
        ResponseEntity<MovimientoDTO> responseEntity = movimientoController.crear(new MovimientoDTO());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(movimientoCreado, responseEntity.getBody());
    }

    @Test
    void listar() {
        List<MovimientoDTO> movimientos = new ArrayList<>();

        when(movimientoService.listar()).thenReturn(movimientos);
        ResponseEntity<List<MovimientoDTO>> responseEntity = movimientoController.listar();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(movimientos, responseEntity.getBody());
    }

    @Test
    void obtenerPorId() {
        MovimientoDTO movimientoExistente = new MovimientoDTO();
        movimientoExistente.setId(1L);

        when(movimientoService.obtenerPorId(1L)).thenReturn(movimientoExistente);
        ResponseEntity<MovimientoDTO> responseEntity = movimientoController.obtenerPorId(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(movimientoExistente, responseEntity.getBody());
    }

    @Test
    void eliminarPorId() {
        movimientoController.eliminarPorId(1L);
        verify(movimientoService).eliminarPorId(1L);
    }

    @Test
    void actualizar() {
        MovimientoDTO movimientoActualizado = new MovimientoDTO();
        movimientoActualizado.setId(1L);

        when(movimientoService.actualizar(any(MovimientoDTO.class))).thenReturn(movimientoActualizado);
        ResponseEntity<MovimientoDTO> responseEntity = movimientoController.actualizar(new MovimientoDTO());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(movimientoActualizado, responseEntity.getBody());
    }
}