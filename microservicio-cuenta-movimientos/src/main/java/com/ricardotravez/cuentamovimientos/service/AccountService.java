package com.ricardotravez.cuentamovimientos.service;

import com.ricardotravez.cuentamovimientos.dto.AccountDTO;
import com.ricardotravez.cuentamovimientos.dto.AccountReport;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountService {

    AccountDTO crear(AccountDTO accountDTO);
    List<AccountDTO> listar();
    AccountDTO obtenerPorId(Long id);
    AccountDTO actualizar(AccountDTO accountDTO);
    void eliminarPorId(Long id);
    List<AccountDTO> findByIdClient(String idClient);


    List<AccountReport> getClientAccountReport(Long idClient, LocalDateTime startDate, LocalDateTime endDate);
}
