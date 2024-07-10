package com.ricardotravez.clientepersona.service.impl;

import com.ricardotravez.clientepersona.clientApi.ClienteApi;
import com.ricardotravez.clientepersona.dto.ClienteDTO;
import com.ricardotravez.clientepersona.dto.CuentaDTO;
import com.ricardotravez.clientepersona.entity.Cliente;
import com.ricardotravez.clientepersona.entity.MensajeError;
import com.ricardotravez.clientepersona.exception.RecursoNoEncontradoException;
import com.ricardotravez.clientepersona.repository.ClienteRepository;
import com.ricardotravez.clientepersona.service.ClienteService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final ClienteApi clienteApi;

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Override
    public ClienteDTO crear(ClienteDTO clienteDTO) {
        Optional<Cliente> clienteExistenteOptional = clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion());
        if (clienteExistenteOptional.isPresent()) {
            throw new RecursoNoEncontradoException("Ya existe un cliente con la misma identificación");
        }
        clienteDTO.setFecha(LocalDate.now());
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        return modelMapper.map(clienteRepository.save(cliente), ClienteDTO.class);
    }

    @Override
    public Observable<ClienteDTO> listar() {
        try {
            return Observable.fromIterable(clienteRepository.findAll())
                    .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                    .flatMap(clienteDTO -> this.obtenerCuentasMovimientosPorClienteIdAsync(clienteDTO));
        } catch (Exception e) {
            logger.error("Error obteniendo datos de cliente", e);
            throw new RecursoNoEncontradoException("Error obteniendo datos de cliente " + e.getMessage());
        }
    }

    @Override
    public ClienteDTO obtenerPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO.toString()));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public Observable<ClienteDTO> obtenerCuentasMovimientosPorClienteIdAsync(ClienteDTO clienteDTO){
        // Crea un Observable para obtener las cuentas de un cliente de manera asíncrona
        Observable<List<CuentaDTO>> cuentasObservable = Observable.fromCallable(() ->
                        clienteApi.getCuentaPorClienteId(clienteDTO.getId().toString()))
                // subscribeOn permite especificar en qué hilo o Scheduler se debe realizar la subscripción inicial
                .subscribeOn(Schedulers.io()) // Schedulers IO -> reutiliza hilos existentes una vez que han terminado su tarea. - Ejecuta la llamada en un hilo de Input/Output
                .doOnNext(cuentas -> {
                    logger.info("Cuentas obtenidas exitosamente para clienteId: " + clienteDTO.getId());
                    clienteDTO.setCuentas(cuentas); // Asigna las cuentas al ClienteDTO
                })
                .doOnError(error -> {
                    logger.error("Error obteniendo cuentas para clienteId: " + clienteDTO.getId(), error);
                    clienteDTO.setCuentas(Collections.emptyList()); // En caso de error, establece una lista vacía
                })
                .onErrorReturnItem(Collections.emptyList()); // En caso de error, devuelve una lista vacía

        // Retorna el ClienteDTO con las cuentas asignadas
        return cuentasObservable.map(cuentas -> clienteDTO);
    }

    @Override
    public ClienteDTO actualizar(ClienteDTO clienteDTO) {

        Cliente clienteDB = modelMapper.map(obtenerPorId(clienteDTO.getId()), Cliente.class);

        clienteDB.setNombre(clienteDTO.getNombre());
        clienteDB.setFecha(clienteDB.getFecha());
        clienteDB.setGenero(clienteDTO.getGenero());
        clienteDB.setEdad(clienteDTO.getEdad());
        clienteDB.setDireccion(clienteDTO.getDireccion());
        clienteDB.setTelefono(clienteDTO.getTelefono());
        clienteDB.setEstado(clienteDTO.isEstado());
        return modelMapper.map(clienteRepository.save(clienteDB), ClienteDTO.class);
    }

    @Override
    public void eliminarPorId(Long id) {
        clienteRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(MensajeError.RECURSO_NO_ENCONTRADO.toString())
        );
        clienteRepository.deleteById(id);
    }

}
