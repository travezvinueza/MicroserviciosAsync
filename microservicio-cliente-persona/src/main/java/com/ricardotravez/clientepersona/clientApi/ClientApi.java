package com.ricardotravez.clientepersona.clientApi;

import com.ricardotravez.clientepersona.dto.AccountDTO;

import java.util.List;

public interface ClientApi {
    List<AccountDTO> getCuentaPorClienteId (String clientId);
}
