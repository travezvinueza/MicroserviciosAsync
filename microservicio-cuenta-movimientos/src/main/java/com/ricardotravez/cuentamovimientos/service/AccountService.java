package com.ricardotravez.cuentamovimientos.service;

import com.ricardotravez.cuentamovimientos.dto.AccountDTO;
import com.ricardotravez.cuentamovimientos.dto.AccountReportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountService {

    AccountDTO create(AccountDTO accountDTO);
    List<AccountDTO> list();
    AccountDTO getById(Long id);
    AccountDTO update(AccountDTO accountDTO);
    void deleteById(Long id);
    List<AccountDTO> findByIdClient(String idClient);


    List<AccountReportDTO> getClientAccountReport(Long idClient, LocalDateTime startDate, LocalDateTime endDate);
}
