package com.electrodostore.cliente_service.controller;

import com.electrodostore.cliente_service.dto.ClienteConVentasDto;
import com.electrodostore.cliente_service.dto.ClienteRequestDto;
import com.electrodostore.cliente_service.dto.ClienteResponseDto;
import com.electrodostore.cliente_service.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    //Inyección de dependencia por constructor para el service de Cliente
    private final IClienteService clienteService;
    public ClienteController(IClienteService clienteService){this.clienteService = clienteService;}

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> findAllClients(){
        return ResponseEntity.ok(clienteService.findAllClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> findCliente(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.findClienteResponse(id));
    }

    @GetMapping("traer-ventas/{clientId}")
    public ResponseEntity<ClienteConVentasDto> findClienteVentas(@PathVariable Long clientId){
        return ResponseEntity.ok(clienteService.findClienteVentas(clientId));
    }

    @PostMapping
    public ResponseEntity<Long> saveCliente(@RequestBody ClienteRequestDto objNuevo){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.saveCliente(objNuevo));
    }

    //Soft Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableCliente(@PathVariable Long id){
        clienteService.disableCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> updateCliente(@PathVariable Long id, @RequestBody ClienteRequestDto updatedClient){
        return ResponseEntity.ok(clienteService.updateCliente(id, updatedClient));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> patchCliente(@PathVariable Long id, @RequestBody ClienteRequestDto updatedClient){
        return ResponseEntity.ok(clienteService.patchCliente(id, updatedClient));
    }


}
