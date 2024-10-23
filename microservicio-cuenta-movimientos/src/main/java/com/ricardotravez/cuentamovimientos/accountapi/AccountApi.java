package com.ricardotravez.cuentamovimientos.accountapi;

import com.ricardotravez.cuentamovimientos.dto.ClientDTO;

public interface AccountApi {
    ClientDTO getCientePorId(String idClient);
}
