package com.ricardotravez.cuentamovimientos.controller;

import com.ricardotravez.cuentamovimientos.dto.AccountDTO;
import com.ricardotravez.cuentamovimientos.dto.AccountReportDTO;
import com.ricardotravez.cuentamovimientos.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> crear(@RequestBody AccountDTO accountDTO){
        return new ResponseEntity<>(accountService.create(accountDTO), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountDTO>> listar(){
        return new ResponseEntity<>(accountService.list(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> obtenerPorId(@PathVariable Long id){
        return new ResponseEntity<>(accountService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable Long id){
        accountService.deleteById(id);
    }

    @PutMapping("/update")
    public ResponseEntity <AccountDTO> actualizar(@RequestBody AccountDTO accountDTO){
        return  new ResponseEntity<>(accountService.update(accountDTO), HttpStatus.OK);
    }

    @GetMapping("/getCuentaPorClienteId/{idClient}")
    public ResponseEntity <List<AccountDTO>> getCuentaPorClienteId(@PathVariable ("idClient") String idClient) {
        return  new ResponseEntity<>(accountService.findByIdClient(idClient), HttpStatus.OK);
    }

    @GetMapping("/report-account")
    public ResponseEntity<List<AccountReportDTO>> obtenerEstadoCuentaPorCliente(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("idClient") Long idClient
    ) {
        return new ResponseEntity<>(accountService.getClientAccountReport(idClient, startDate.atStartOfDay(), endDate.atStartOfDay()), HttpStatus.OK);
    }
}
