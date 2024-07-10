package com.ricardotravez.clientepersona.clientApi.impl;

import com.ricardotravez.clientepersona.clientApi.ClienteApi;
import com.ricardotravez.clientepersona.dto.CuentaDTO;
import com.ricardotravez.clientepersona.dto.MovimientoDTO;
import com.ricardotravez.clientepersona.exception.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteApiImpl implements ClienteApi {

    private final RestTemplate restTemplate;

    @Override
    public List<CuentaDTO> getCuentaPorClienteId(String clientId) {
        String url = "http://localhost:8081/api/v1/cuentas/getCuentaPorClienteId/" + clientId;
        ResponseEntity<List<CuentaDTO>> responseCuenta = restTemplate.exchange(url, HttpMethod.GET, null,  new ParameterizedTypeReference<>() {});
        if(responseCuenta.getStatusCode().value() == 200) {
            return responseCuenta.getBody();

        }
        throw new RecursoNoEncontradoException ("Error en el servicio");
    }

    @Override
    public List<MovimientoDTO> getMovimientoPorClienteId(String clientId) {
        String url = "http://localhost:8081/api/v1/cuentas/getCuentaPorClienteId/" + clientId;
        ResponseEntity<List<MovimientoDTO>> responseCuenta = restTemplate.exchange(url, HttpMethod.GET, null,  new ParameterizedTypeReference<>() {});
        if(responseCuenta.getStatusCode().value() == 200) {
            return responseCuenta.getBody();

        }
        throw new RecursoNoEncontradoException ("Error en el servicio");
    }
}
