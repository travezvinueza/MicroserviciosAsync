package com.ricardotravez.cuentamovimientos.cuentaapi;

import com.ricardotravez.cuentamovimientos.dto.ClienteDTO;

public interface CuentaApi {
    ClienteDTO getCientePorId(String idCliente);
}
