package com.ricardotravez.cuentamovimientos.controller;

import com.ricardotravez.cuentamovimientos.dto.CuentaDTO;
import com.ricardotravez.cuentamovimientos.service.CuentaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CuentaControllerTest {

    @InjectMocks
    private CuentaController cuentaController;
    @Mock
    private CuentaService cuentaService;


    @Test
    void crear() {
        CuentaDTO cuentaCreada = new CuentaDTO();
        cuentaCreada.setId(1L);

        when(cuentaService.crear(any(CuentaDTO.class))).thenReturn(cuentaCreada);
        ResponseEntity<CuentaDTO> responseEntity = cuentaController.crear(new CuentaDTO());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(cuentaCreada, responseEntity.getBody());
    }

    @Test
    void listar() {

        List<CuentaDTO> cuentas = new ArrayList<>();

        when(cuentaService.listar()).thenReturn(cuentas);
        ResponseEntity<List<CuentaDTO>> responseEntity = cuentaController.listar();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cuentas, responseEntity.getBody());
    }

    @Test
    void obtenerPorId() {
        CuentaDTO cuentaExistente = new CuentaDTO();
        cuentaExistente.setId(1L);

        when(cuentaService.obtenerPorId(1L)).thenReturn(cuentaExistente);
        ResponseEntity<CuentaDTO> responseEntity = cuentaController.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cuentaExistente, responseEntity.getBody());
    }

    @Test
    void eliminarPorId() {
        cuentaController.eliminarPorId(1L);
        verify(cuentaService).eliminarPorId(1L);
    }

    @Test
    void actualizar() {
        CuentaDTO cuentaActualizada = new CuentaDTO();
        cuentaActualizada.setId(1L);

        when(cuentaService.actualizar(any(CuentaDTO.class))).thenReturn(cuentaActualizada);
        ResponseEntity<CuentaDTO> responseEntity = cuentaController.actualizar(new CuentaDTO());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cuentaActualizada, responseEntity.getBody());
    }

    @Test
    void getCuentaPorClienteId() {
        //Simulo
        List<CuentaDTO> cuentasCliente = new ArrayList<>();
        // Mockear
        when(cuentaService.findByIdCliente(anyString())).thenReturn(cuentasCliente);
        // Llamar al método del controlador
        ResponseEntity<List<CuentaDTO>> responseEntity = cuentaController.getCuentaPorClienteId("clienteId");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cuentasCliente, responseEntity.getBody());
    }
}
