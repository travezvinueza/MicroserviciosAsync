package com.ricardotravez.clientepersona.clientApi.impl;

import com.ricardotravez.clientepersona.clientApi.ClientApi;
import com.ricardotravez.clientepersona.dto.AccountDTO;
import com.ricardotravez.clientepersona.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientApiImpl implements ClientApi {
    private final RestTemplate restTemplate;

    @Override
    public List<AccountDTO> getCuentaPorClienteId(String clientId) {
        String url = "http://cuenta-movimientos:8081/api/v1/accounts/getCuentaPorClienteId/" + clientId;
        ResponseEntity<List<AccountDTO>> responseCuenta = restTemplate.exchange(url, HttpMethod.GET, null,  new ParameterizedTypeReference<>() {});
        if(responseCuenta.getStatusCode().value() == 200) {
            return responseCuenta.getBody();

        }
        throw new ResourceNotFoundException("Error en el servicio");
    }

}
