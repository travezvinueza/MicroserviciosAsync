package com.ricardotravez.clientepersona.controller;

import com.ricardotravez.clientepersona.dto.ClientDTO;
import com.ricardotravez.clientepersona.service.ClientService;
import io.reactivex.rxjava3.core.Observable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity <ClientDTO> crear(@RequestBody ClientDTO clientDTO){
        return  new ResponseEntity<>(clientService.create(clientDTO), HttpStatus.CREATED) ;
    }

    @GetMapping("/list")
    public ResponseEntity<Observable<ClientDTO>> listar(){
        return new ResponseEntity<>(clientService.list(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> obtenerPorId(@PathVariable Long id){
        return new ResponseEntity<>(clientService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable Long id){
        clientService.deleteById(id);
    }

    @PutMapping("/update")
    public ResponseEntity <ClientDTO> actualizar(@RequestBody ClientDTO clientDTO){
        return  new ResponseEntity<>(clientService.update(clientDTO), HttpStatus.OK);
    }

}
