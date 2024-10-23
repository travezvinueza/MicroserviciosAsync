package com.ricardotravez.cuentamovimientos.controller;

import com.ricardotravez.cuentamovimientos.dto.AccountDTO;
import com.ricardotravez.cuentamovimientos.service.AccountService;
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
class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;
    @Mock
    private AccountService accountService;

    @Test
    void create() {
        AccountDTO cuentaCreada = new AccountDTO();
        cuentaCreada.setId(1L);

        when(accountService.create(any(AccountDTO.class))).thenReturn(cuentaCreada);
        ResponseEntity<AccountDTO> responseEntity = accountController.crear(new AccountDTO());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(cuentaCreada, responseEntity.getBody());
    }

    @Test
    void list() {

        List<AccountDTO> cuentas = new ArrayList<>();

        when(accountService.list()).thenReturn(cuentas);
        ResponseEntity<List<AccountDTO>> responseEntity = accountController.listar();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cuentas, responseEntity.getBody());
    }

    @Test
    void getById() {
        AccountDTO cuentaExistente = new AccountDTO();
        cuentaExistente.setId(1L);

        when(accountService.getById(1L)).thenReturn(cuentaExistente);
        ResponseEntity<AccountDTO> responseEntity = accountController.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cuentaExistente, responseEntity.getBody());
    }

    @Test
    void deleteById() {
        accountController.eliminarPorId(1L);
        verify(accountService).deleteById(1L);
    }

    @Test
    void update() {
        AccountDTO cuentaActualizada = new AccountDTO();
        cuentaActualizada.setId(1L);

        when(accountService.update(any(AccountDTO.class))).thenReturn(cuentaActualizada);
        ResponseEntity<AccountDTO> responseEntity = accountController.actualizar(new AccountDTO());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cuentaActualizada, responseEntity.getBody());
    }

    @Test
    void getAccountByClientId() {
        //Simulo
        List<AccountDTO> cuentasCliente = new ArrayList<>();
        // Mockear
        when(accountService.findByIdClient(anyString())).thenReturn(cuentasCliente);
        // Llamar al método del controlador
        ResponseEntity<List<AccountDTO>> responseEntity = accountController.getCuentaPorClienteId("clientId");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cuentasCliente, responseEntity.getBody());
    }
}
