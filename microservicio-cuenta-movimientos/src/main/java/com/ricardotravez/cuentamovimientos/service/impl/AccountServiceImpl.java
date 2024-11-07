package com.ricardotravez.cuentamovimientos.service.impl;

import com.ricardotravez.cuentamovimientos.accountapi.AccountApi;
import com.ricardotravez.cuentamovimientos.dto.ClientDTO;
import com.ricardotravez.cuentamovimientos.dto.AccountDTO;
import com.ricardotravez.cuentamovimientos.dto.AccountReportDTO;
import com.ricardotravez.cuentamovimientos.dto.AccountReportDetailDTO;
import com.ricardotravez.cuentamovimientos.dto.MotionDTO;
import com.ricardotravez.cuentamovimientos.entity.Account;
import com.ricardotravez.cuentamovimientos.dto.MessageError;
import com.ricardotravez.cuentamovimientos.exception.AccountReportException;
import com.ricardotravez.cuentamovimientos.exception.ClientNotFoundException;
import com.ricardotravez.cuentamovimientos.exception.ResourceNotFoundException;
import com.ricardotravez.cuentamovimientos.repository.AccountRepository;
import com.ricardotravez.cuentamovimientos.service.AccountService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final AccountApi accountApi;
    private final Random random = new Random();

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        try {
            // Creo un Observable para obtener el cliente desde la API de manera asíncrona
            Observable<ClientDTO> clienteObservable = Observable.fromCallable(() ->
                            accountApi.getCientePorId(accountDTO.getIdClient()))
                    .subscribeOn(Schedulers.io()) //Asegura que las llamadas a la API se realicen en un hilo adecuado para operaciones de entrada/salida.
                    .doOnError(error -> log.error("Error obteniendo cliente: ", error))
                    .onErrorReturnItem(new ClientDTO());

            ClientDTO clientDTO = clienteObservable.blockingFirst();

            if (clientDTO.getId() == null) {
                throw new ClientNotFoundException("Error cliente no existe no se puede crear la cuenta");
            }

            Account account = modelMapper.map(accountDTO, Account.class);
            account.setDate(LocalDateTime.now());
            account.setAccountNumber(generateAccountNumber());
            // Guarda la entidad de cuenta en el repositorio y l
            return modelMapper.map(accountRepository.save(account), AccountDTO.class);
        } catch (Exception e) {
            throw new ClientNotFoundException("Error: " + e.getMessage());
        }
    }

    private String generateAccountNumber() {
        int min = 100000;
        int max = 999999;
        String accountNumber;
        do {
            accountNumber = String.valueOf(random.nextInt(max - min + 1) + min);
        } while (accountRepository.findByAccountNumber(accountNumber).isPresent());

        return accountNumber;
    }

    @Override
    public List<AccountDTO> list() {
        return accountRepository.findAll().stream()
                .map(cuenta -> {
                    AccountDTO accountDTO = modelMapper.map(cuenta, AccountDTO.class);
                    accountDTO.setDate(LocalDateTime.now());
                    return accountDTO;
                })
                .toList();
    }

    @Override
    public AccountDTO getById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MessageError.RECURSO_NO_ENCONTRADO.toString()));
        return modelMapper.map(account, AccountDTO.class);
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO) {
        AccountDTO existingAccount  = getById(accountDTO.getId());

        existingAccount.setDate(LocalDateTime.now());
        existingAccount.setAccountType(accountDTO.getAccountType());
        existingAccount.setInitialBalance(accountDTO.getInitialBalance());
        existingAccount.setState(accountDTO.isState());
        Account account = modelMapper.map(existingAccount, Account.class);

        return modelMapper.map(accountRepository.save(account), AccountDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.delete(
                accountRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException(MessageError.RECURSO_NO_ENCONTRADO.toString())
                )
        );
    }

    @Override
    public List<AccountDTO> findByIdClient(String idClient) {
        List<Account> accounts = accountRepository.findByIdClient(idClient);
        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("No existe ningún usuario con el ID proporcionado: " + idClient);
        }
        return accounts.stream().map(cuenta -> {
            AccountDTO accountDTO = modelMapper.map(cuenta, AccountDTO.class);
            accountDTO.setDate(LocalDateTime.now());
            List<MotionDTO> motionDTOS = cuenta.getMotions()
                    .stream()
                    .map(movimiento -> modelMapper.map(movimiento, MotionDTO.class))
                    .toList();
            accountDTO.setMotions(motionDTOS);
            return accountDTO;
        }).toList();
    }

    @Override
    public List<AccountReportDTO> getClientAccountReport(Long idClient, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            // Establecer conexion con servicio cliente para obtener datos de cliente
            Observable<ClientDTO> clienteObservable = Observable.fromCallable(() ->
                            accountApi.getCientePorId(idClient.toString()))
                    .subscribeOn(Schedulers.io()) //Asegura que las llamadas a la API se realicen en un hilo adecuado para operaciones de entrada/salida.
                    .doOnError(error -> log.error("Error obteniendo cliente: ", error))
                    .onErrorReturnItem(new ClientDTO());

            ClientDTO clientDTO = clienteObservable.blockingFirst();
            if (clientDTO.getId() == null) {
                throw new ClientNotFoundException("Error cliente no existe no se puede generar estado de cuentas");
            }

            //Ajustar el endDate para incluir todoo el día
            LocalDateTime adjustedEndDate = endDate.plusDays(1).minusSeconds(1);

            // Buscar la cuenta y sus movimientos utilizando el repositorio
            List<Account> accounts = accountRepository.findByIdClientAndDateBetween(idClient.toString(), startDate, adjustedEndDate);

            if (accounts.isEmpty()) {
                throw new ResourceNotFoundException("El cliente no contiene ninguna cuenta asociada");
            }

            List<AccountReportDTO> accountReportDTOS = new ArrayList<>();

            accounts.forEach(account -> {
                List<AccountReportDetailDTO> accountReportDetailDTOS = account.getMotions().stream()
                        .map(motion -> {
                            AccountReportDetailDTO detalleDTO = modelMapper.map(motion, AccountReportDetailDTO.class);
                            detalleDTO.setDate(motion.getDate().toLocalDate()); // Ajusta la fecha del movimiento
                            return detalleDTO;
                        })
                        .toList();

                AccountReportDTO accountReportDTO = modelMapper.map(account, AccountReportDTO.class);
                accountReportDTO.setAccountReportDetail(accountReportDetailDTOS);
                accountReportDTO.setDate(LocalDate.now());
                accountReportDTO.setClient(clientDTO);

                // Añadir el DTO del reporte a la lista de reportes
                accountReportDTOS.add(accountReportDTO);
            });

            return accountReportDTOS;
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            log.error("Error al obtener el reporte de la cuenta: ", e);
            throw new AccountReportException("Error al obtener el reporte de la cuenta: " + e.getMessage());
        }
    }

}
