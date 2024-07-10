package com.ricardotravez.clientepersona.controller;

import com.ricardotravez.clientepersona.dto.ClienteDTO;
import com.ricardotravez.clientepersona.service.ClienteService;
import io.reactivex.rxjava3.core.Observable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/crear")
    public ResponseEntity <ClienteDTO> crear(@RequestBody ClienteDTO clienteDTO){
        return  new ResponseEntity<>(clienteService.crear(clienteDTO), HttpStatus.CREATED) ;
    }

    @GetMapping("/listar")
    public ResponseEntity<Observable<ClienteDTO>> listar(){
        return new ResponseEntity<>(clienteService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerPorId(@PathVariable Long id){
        return new ResponseEntity<>(clienteService.obtenerPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable Long id){
        clienteService.eliminarPorId(id);
    }

    @PutMapping("/actualizar")
    public ResponseEntity <ClienteDTO> actualizar(@RequestBody ClienteDTO clienteDTO){
        return  new ResponseEntity<>(clienteService.actualizar(clienteDTO), HttpStatus.OK);
    }

}
