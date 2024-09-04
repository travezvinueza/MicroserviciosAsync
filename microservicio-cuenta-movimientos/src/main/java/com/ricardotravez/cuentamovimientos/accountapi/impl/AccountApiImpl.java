package com.ricardotravez.cuentamovimientos.accountapi.impl;

import com.ricardotravez.cuentamovimientos.accountapi.AccountApi;
import com.ricardotravez.cuentamovimientos.dto.ClientDTO;
import com.ricardotravez.cuentamovimientos.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AccountApiImpl implements AccountApi {
    private final RestTemplate restTemplate;

    @Override
    public ClientDTO getCientePorId(String idClient) {
        try {
            String apiEndpoint  = "http://localhost:8080/api/v1/clients/" + idClient;
            ResponseEntity<ClientDTO> response = restTemplate.exchange(apiEndpoint, HttpMethod.GET, null, ClientDTO.class);
            if(response.getStatusCode().value() == 200){
                return response.getBody();
            }
            throw new ClientNotFoundException("Error, response mal formado");
        }catch (Exception e){
            throw new ClientNotFoundException("Error, cliente no existe");
        }
    }
}
