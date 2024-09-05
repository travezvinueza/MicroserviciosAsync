package com.ricardotravez.clientepersona.controller;

import com.ricardotravez.clientepersona.dto.ClientDTO;
import com.ricardotravez.clientepersona.dto.enums.GenderPerson;
import com.ricardotravez.clientepersona.service.ClientService;
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
class ClientControllerTest {
    @InjectMocks
    private ClientController clientController;
    @Mock
    private ClientService clientService;

    @Test
    void createClient() {
        when(clientService.create(any(ClientDTO.class)))
                .thenAnswer(invocation -> {
                    ClientDTO clientDTOArgument = invocation.getArgument(0);
                    return new ClientDTO(
                            1L,
                            clientDTOArgument.getName(),
                            clientDTOArgument.getGenderPerson(),
                            clientDTOArgument.getAge(),
                            clientDTOArgument.getIdentificationNumber(),
                            clientDTOArgument.getAddress(),
                            LocalDate.now(),
                            clientDTOArgument.getPhone(),
                            clientDTOArgument.getPassword(),
                            clientDTOArgument.isState(),
                            new ArrayList<>()
                    );
                });

        ResponseEntity<ClientDTO> clienteDTO = clientController.crear(
                new ClientDTO(
                        null,
                        "Ricardo",
                        GenderPerson.MASCULINO,
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

        Assertions.assertEquals(1L, clienteDTO.getBody().getId());
    }

    @Test
    void listClients() {
        List<ClientDTO> clientes = new ArrayList<>();
        clientes.add(new ClientDTO(1L, "Cliente 1", GenderPerson.MASCULINO, 30, "ID1", "Dirección 1", LocalDate.now(), "123456789", "contraseña", true, new ArrayList<>()));
        clientes.add(new ClientDTO(2L, "Cliente 2", GenderPerson.FEMENINO, 25, "ID2", "Dirección 2", LocalDate.now(), "987654321", "contraseña2", true, new ArrayList<>()));

        when(clientService.list()).thenReturn(Observable.fromIterable(clientes));
        ResponseEntity<Observable<ClientDTO>> responseEntity = clientController.listar();

        // Convertir el Observable a una lista para las aserciones
        List<ClientDTO> clientesResponse = responseEntity.getBody().toList().blockingGet();

        Assertions.assertEquals(clientes, clientesResponse);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getClientById() {
        ClientDTO clienteExistente = new ClientDTO(1L, "Cliente 1", GenderPerson.MASCULINO, 30, "ID1", "Dirección 1",LocalDate.now(), "123456789", "contraseña", true, new ArrayList<>());

        when(clientService.getById(1L)).thenReturn(clienteExistente);
        ResponseEntity<ClientDTO> responseEntity = clientController.obtenerPorId(1L);
        Assertions.assertEquals(responseEntity.getBody(), clienteExistente);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteClientById() {
        clientController.eliminarPorId(1L);
        verify(clientService).deleteById(1L);
    }

    @Test
    void updateClient() {
        ClientDTO clienteActualizado = new ClientDTO(1L, "Cliente Actualizado",  GenderPerson.FEMENINO, 35, "ID1", "Nueva Dirección", LocalDate.now(),"987654321", "nuevaContraseña", true,  new ArrayList<>());

        when(clientService.update(any(ClientDTO.class))).thenReturn(clienteActualizado);
        ResponseEntity<ClientDTO> responseEntity = clientController.actualizar(clienteActualizado);
        Assertions.assertEquals(responseEntity.getBody(), clienteActualizado);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}