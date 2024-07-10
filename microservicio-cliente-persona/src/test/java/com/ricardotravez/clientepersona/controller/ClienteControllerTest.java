package com.ricardotravez.clientepersona.controller;

import com.ricardotravez.clientepersona.dto.ClienteDTO;
import com.ricardotravez.clientepersona.service.ClienteService;
import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {
    @InjectMocks
    private ClienteController clienteController;
    @Mock
    private ClienteService clienteService;

    @Test
    void crearCliente() {
        when(clienteService.crear(any(ClienteDTO.class)))
                .thenAnswer(invocation -> {
                    ClienteDTO clienteDTOArgument = invocation.getArgument(0);
                    return new ClienteDTO(
                            1L,
                            clienteDTOArgument.getNombre(),
                            clienteDTOArgument.getGenero(),
                            clienteDTOArgument.getEdad(),
                            clienteDTOArgument.getIdentificacion(),
                            clienteDTOArgument.getDireccion(),
                            LocalDate.now(),
                            clienteDTOArgument.getTelefono(),
                            clienteDTOArgument.getContrasena(),
                            clienteDTOArgument.isEstado(),
                            new ArrayList<>()
                    );
                });

        ResponseEntity<ClienteDTO> clienteDTO = clienteController.crear(
                new ClienteDTO(
                        null,
                        "Ricardo",
                        "Masculino",
                        29,
                        "1723949291",
                        "Floresta",
                        LocalDate.now(),
                        "0979317536",
                        "123456",
                        true,
                        new ArrayList<>()
                )
        );

        Assertions.assertEquals(clienteDTO.getBody().getId(), 1L);
    }

    @Test
    void listarClientes() {
        List<ClienteDTO> clientes = new ArrayList<>();
        clientes.add(new ClienteDTO(1L, "Cliente 1", "Masculino", 30, "ID1", "Dirección 1", LocalDate.now(), "123456789", "contraseña", true, new ArrayList<>()));
        clientes.add(new ClienteDTO(2L, "Cliente 2", "Femenino", 25, "ID2", "Dirección 2", LocalDate.now(), "987654321", "contraseña2", true, new ArrayList<>()));

        when(clienteService.listar()).thenReturn(Observable.fromIterable(clientes));
        ResponseEntity<Observable<ClienteDTO>> responseEntity = clienteController.listar();

        // Convertir el Observable a una lista para las aserciones
        List<ClienteDTO> clientesResponse = responseEntity.getBody().toList().blockingGet();

        Assertions.assertEquals(clientes, clientesResponse);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void obtenerClientePorId() {
        ClienteDTO clienteExistente = new ClienteDTO(1L, "Cliente 1", "Masculino", 30, "ID1", "Dirección 1",LocalDate.now(), "123456789", "contraseña", true, new ArrayList<>());

        when(clienteService.obtenerPorId(1L)).thenReturn(clienteExistente);
        ResponseEntity<ClienteDTO> responseEntity = clienteController.obtenerPorId(1L);
        Assertions.assertEquals(responseEntity.getBody(), clienteExistente);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void eliminarClientePorId() {
        clienteController.eliminarPorId(1L);
        verify(clienteService).eliminarPorId(1L);
    }

    @Test
    void actualizarCliente() {
        ClienteDTO clienteActualizado = new ClienteDTO(1L, "Cliente Actualizado", "Femenino", 35, "ID1", "Nueva Dirección", LocalDate.now(),"987654321", "nuevaContraseña", true,  new ArrayList<>());

        when(clienteService.actualizar(any(ClienteDTO.class))).thenReturn(clienteActualizado);
        ResponseEntity<ClienteDTO> responseEntity = clienteController.actualizar(clienteActualizado);
        Assertions.assertEquals(responseEntity.getBody(), clienteActualizado);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

}