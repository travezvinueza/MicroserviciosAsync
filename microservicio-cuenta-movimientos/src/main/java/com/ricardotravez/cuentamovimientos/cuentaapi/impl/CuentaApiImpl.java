package com.ricardotravez.cuentamovimientos.cuentaapi.impl;

import com.ricardotravez.cuentamovimientos.cuentaapi.CuentaApi;
import com.ricardotravez.cuentamovimientos.dto.ClienteDTO;
import com.ricardotravez.cuentamovimientos.exception.ClienteNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CuentaApiImpl implements CuentaApi {

    private final RestTemplate restTemplate;

    private String URI = "http://cliente-personas:8080/api/v1/clientes";

    @Override
    public ClienteDTO getCientePorId(String idCliente) {
        try {
            String urlCliente = URI + "/" +idCliente;
            ResponseEntity<ClienteDTO> response = restTemplate.exchange(urlCliente, HttpMethod.GET, null, ClienteDTO.class);
            if(response.getStatusCode().value() == 200){
                return response.getBody();
            }
            throw new ClienteNoEncontradoException("Error, response mal formado");
        }catch (Exception e){
            throw new ClienteNoEncontradoException("Error, cliente no existe");
        }
    }
}
