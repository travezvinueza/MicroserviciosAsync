package com.ricardotravez.cuentamovimientos.controller;

import com.ricardotravez.cuentamovimientos.dto.CuentaDTO;
import com.ricardotravez.cuentamovimientos.dto.CuentaReporteDTO;
import com.ricardotravez.cuentamovimientos.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping("/crear")
    public ResponseEntity<CuentaDTO> crear(@RequestBody CuentaDTO cuentaDTO){
        return new ResponseEntity<>(cuentaService.crear(cuentaDTO), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CuentaDTO>> listar(){
        return new ResponseEntity<>(cuentaService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> obtenerPorId(@PathVariable Long id){
        return new ResponseEntity<>(cuentaService.obtenerPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable Long id){
        cuentaService.eliminarPorId(id);
    }

    @PutMapping("/actualizar")
    public ResponseEntity <CuentaDTO> actualizar(@RequestBody CuentaDTO cuentaDTO){
        return  new ResponseEntity<>(cuentaService.actualizar(cuentaDTO), HttpStatus.OK);
    }

    @GetMapping("/getCuentaPorClienteId/{idCliente}")
    public ResponseEntity <List<CuentaDTO>> getCuentaPorClienteId(@PathVariable ("idCliente") String idCliente) {
        return  new ResponseEntity<>(cuentaService.findByIdCliente(idCliente), HttpStatus.OK);
    }

    @GetMapping("/reporte-cuenta")
    public ResponseEntity<List<CuentaReporteDTO>> obtenerEstadoCuentaPorCliente(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam("idCliente") Long idCliente
    ) {
        return new ResponseEntity<>(cuentaService.obtenerReporteCuentaCliente(idCliente, fechaInicio.atStartOfDay(), fechaFin.atStartOfDay()), HttpStatus.OK);
    }
}
