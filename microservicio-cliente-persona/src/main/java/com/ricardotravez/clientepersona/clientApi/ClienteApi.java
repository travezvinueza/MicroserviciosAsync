package com.ricardotravez.clientepersona.clientApi;

import com.ricardotravez.clientepersona.dto.CuentaDTO;
import com.ricardotravez.clientepersona.dto.MovimientoDTO;

import java.util.List;

public interface ClienteApi {
    List<CuentaDTO> getCuentaPorClienteId (String clientId);
    List<MovimientoDTO> getMovimientoPorClienteId (String clientId);
}
