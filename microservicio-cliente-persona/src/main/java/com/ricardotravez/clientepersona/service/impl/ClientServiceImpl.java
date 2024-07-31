package com.ricardotravez.clientepersona.service.impl;

import com.ricardotravez.clientepersona.clientApi.ClientApi;
import com.ricardotravez.clientepersona.dto.ClientDTO;
import com.ricardotravez.clientepersona.dto.AccountDTO;
import com.ricardotravez.clientepersona.entity.Client;
import com.ricardotravez.clientepersona.entity.MessageError;
import com.ricardotravez.clientepersona.exception.ResourceNotFoundException;
import com.ricardotravez.clientepersona.repository.ClientRepository;
import com.ricardotravez.clientepersona.service.ClientService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final ClientApi clientApi;

    @Override
    public ClientDTO create(ClientDTO clientDTO) {
        Optional<Client> clienteExistenteOptional = clientRepository.findByIdentificationNumber(clientDTO.getIdentificationNumber());
        if (clienteExistenteOptional.isPresent()) {
            throw new ResourceNotFoundException("Ya existe un cliente con la misma identificación");
        }
        clientDTO.setDate(LocalDate.now());
        Client client = modelMapper.map(clientDTO, Client.class);
        return modelMapper.map(clientRepository.save(client), ClientDTO.class);
    }

    @Override
    public Observable<ClientDTO> list() {
        try {
            // Crea un Observable a partir de la lista de todos los clientes en el repositorio
            return Observable.fromIterable(clientRepository.findAll())
                    .map(client -> modelMapper.map(client, ClientDTO.class))
                    // Obtiene las cuentas y movimientos de cada cliente de manera asíncrona
                    .flatMap(clientDTO -> this.obtenerCuentasMovimientosPorClienteIdAsync(clientDTO));
        } catch (Exception e) {
            log.error("Error obteniendo datos de cliente", e);
            throw new ResourceNotFoundException("Error obteniendo datos de cliente " + e.getMessage());
        }
    }

    @Override
    public ClientDTO getById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageError.RECURSO_NO_ENCONTRADO.toString()));
        return modelMapper.map(client, ClientDTO.class);
    }

    public Observable<ClientDTO> obtenerCuentasMovimientosPorClienteIdAsync(ClientDTO clientDTO){
        // Crea un Observable para obtener las cuentas de un cliente de manera asíncrona
        Observable<List<AccountDTO>> cuentasObservable = Observable.fromCallable(() ->
                        clientApi.getCuentaPorClienteId(clientDTO.getId().toString()))
                .subscribeOn(Schedulers.io()) // Ejecuta la llamada en un subproceso de I/O
                .doOnNext(cuentas -> {
                    log.info("Cuentas obtenidas exitosamente para clienteId: " + clientDTO.getId());
                    clientDTO.setAccounts(cuentas); // Asigna las cuentas al ClienteDTO
                })
                .doOnError(error -> {
                    log.error("Error obteniendo cuentas para clienteId: " + clientDTO.getId(), error);
                    clientDTO.setAccounts(Collections.emptyList()); // En caso de error, establece una lista vacía
                })
                .onErrorReturnItem(Collections.emptyList());

        // Retorna el ClienteDTO con las cuentas asignadas
        return cuentasObservable.map(cuentas -> clientDTO);
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO) {

        Client clientDB = modelMapper.map(getById(clientDTO.getId()), Client.class);

        clientDB.setName(clientDTO.getName());
        clientDB.setDate(clientDB.getDate());
        clientDB.setGenderPerson(clientDTO.getGenderPerson());
        clientDB.setAge(clientDTO.getAge());
        clientDB.setAddress(clientDTO.getAddress());
        clientDB.setPhone(clientDTO.getPhone());
        clientDB.setState(clientDTO.isState());
        return modelMapper.map(clientRepository.save(clientDB), ClientDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(MessageError.RECURSO_NO_ENCONTRADO.toString())
        );
        clientRepository.deleteById(id);
    }

}
