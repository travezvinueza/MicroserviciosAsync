package com.ricardotravez.cuentamovimientos.controller;

import com.ricardotravez.cuentamovimientos.dto.MotionDTO;
import com.ricardotravez.cuentamovimientos.service.MotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/motions")
@RequiredArgsConstructor
public class MotionController {
    private final MotionService motionService;

    @PostMapping("/create")
    public ResponseEntity<MotionDTO> crear(@RequestBody MotionDTO motionDTO){
        return new ResponseEntity<>(motionService.create(motionDTO), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MotionDTO>> listar(){
        return new ResponseEntity<>(motionService.list(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotionDTO> obtenerPorId(@PathVariable Long id){
        return new ResponseEntity<>(motionService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable Long id){
        motionService.deleteById(id);
    }

    @PutMapping("/update")
    public ResponseEntity <MotionDTO> actualizar(@RequestBody MotionDTO motionDTO){
        return  new ResponseEntity<>(motionService.update(motionDTO), HttpStatus.OK);
    }

    @GetMapping("/getMovimientosPorClienteId/{idClient}")
    public ResponseEntity <List<MotionDTO>> getMovimientosPorClienteId(@PathVariable ("idClient") String idClient) {
        return  new ResponseEntity<>(motionService.findByIdClient(idClient), HttpStatus.OK);
    }

}
