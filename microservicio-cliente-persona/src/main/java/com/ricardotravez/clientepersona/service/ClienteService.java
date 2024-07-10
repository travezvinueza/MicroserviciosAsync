package com.ricardotravez.clientepersona.service;

import com.ricardotravez.clientepersona.dto.ClienteDTO;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public interface ClienteService {
    ClienteDTO crear(ClienteDTO clienteDTO);
    Observable<ClienteDTO> listar();
    ClienteDTO obtenerPorId(Long id);
    ClienteDTO actualizar(ClienteDTO clienteDTO);
    void eliminarPorId(Long id);
}
