package com.ricardotravez.cuentamovimientos.controller;

import com.ricardotravez.cuentamovimientos.dto.MovimientoDTO;
import com.ricardotravez.cuentamovimientos.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping("/crear")
    public ResponseEntity<MovimientoDTO> crear(@RequestBody MovimientoDTO movimientoDTO){
        return new ResponseEntity<>(movimientoService.crear(movimientoDTO), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MovimientoDTO>> listar(){
        return new ResponseEntity<>(movimientoService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> obtenerPorId(@PathVariable Long id){
        return new ResponseEntity<>(movimientoService.obtenerPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable Long id){
        movimientoService.eliminarPorId(id);
    }

    @PutMapping("/actualizar")
    public ResponseEntity <MovimientoDTO> actualizar(@RequestBody MovimientoDTO movimientoDTO){
        return  new ResponseEntity<>(movimientoService.actualizar(movimientoDTO), HttpStatus.OK);
    }

    @GetMapping("/getMovimientosPorClienteId/{idCliente}")
    public ResponseEntity <List<MovimientoDTO>> getMovimientosPorClienteId(@PathVariable ("idCliente") String idCliente) {
        return  new ResponseEntity<>(movimientoService.findByIdCliente(idCliente), HttpStatus.OK);
    }

}
