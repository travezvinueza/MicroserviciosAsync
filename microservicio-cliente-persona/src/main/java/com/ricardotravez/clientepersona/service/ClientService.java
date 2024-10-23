package com.ricardotravez.clientepersona.service;

import com.ricardotravez.clientepersona.dto.ClientDTO;
import io.reactivex.rxjava3.core.Observable;

public interface ClientService {
    ClientDTO create(ClientDTO clientDTO);
    Observable<ClientDTO> list();
    ClientDTO getById(Long id);
    ClientDTO update(ClientDTO clientDTO);
    void deleteById(Long id);
}
