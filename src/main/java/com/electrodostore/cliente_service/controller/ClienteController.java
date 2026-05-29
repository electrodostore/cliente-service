package com.electrodostore.cliente_service.controller;

import com.electrodostore.cliente_service.dto.ClienteConVentasDto;
import com.electrodostore.cliente_service.dto.ClientePatchRequestDto;
import com.electrodostore.cliente_service.dto.ClienteRequestDto;
import com.electrodostore.cliente_service.dto.ClienteResponseDto;
import com.electrodostore.cliente_service.service.IClienteService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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

    //Consulta administrativa de un cliente por su id
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> findCliente(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.findClienteResponse(id));
    }

    //Consulta solo clientes habilitados para realizar operaciones comerciales
    @GetMapping("/{id}/enabled")
    public ResponseEntity<ClienteResponseDto> findActiveClient(@PathVariable Long id){
        return ResponseEntity.ok(
                clienteService.findActiveClient(id)
        );
    }

    @GetMapping("/{clientId}/ventas")
    public ResponseEntity<ClienteConVentasDto> findClienteVentas(@PathVariable Long clientId){
        return ResponseEntity.ok(clienteService.findClienteVentas(clientId));
    }

    @PostMapping
    public ResponseEntity<Long> saveCliente(@Valid @RequestBody ClienteRequestDto objNuevo){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.saveCliente(objNuevo));
    }

    //Soft Delete
    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disableCliente(@PathVariable Long id){
        clienteService.disableCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDto updatedClient){
        return ResponseEntity.ok(clienteService.updateCliente(id, updatedClient));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> patchCliente(@PathVariable Long id, @RequestBody @Valid ClientePatchRequestDto updatedClient){
        return ResponseEntity.ok(clienteService.patchCliente(id, updatedClient));
    }

    //Busca datos del cliente autenticado
    @GetMapping("/me")
    public ResponseEntity<ClienteResponseDto> findMe(){
        return ResponseEntity.ok(
                clienteService.findMe()
        );
    }

    @PutMapping("/me")
    public ResponseEntity<ClienteResponseDto> updateMe(@Valid @RequestBody ClienteRequestDto updatedClient){
        return ResponseEntity.ok(
                clienteService.updateMe(updatedClient)
        );
    }

    @PatchMapping("/me")
    public ResponseEntity<ClienteResponseDto> patchMe(@RequestBody @Valid ClientePatchRequestDto updatedClient){
        return ResponseEntity.ok(
                clienteService.patchMe(updatedClient)
        );
    }

    @GetMapping("/me/ventas")
    public ResponseEntity<ClienteConVentasDto> findVentas(){
        return ResponseEntity.ok(
                clienteService.findMyVentas()
        );
    }



}
