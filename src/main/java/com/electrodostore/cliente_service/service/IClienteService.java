package com.electrodostore.cliente_service.service;

import com.electrodostore.cliente_service.dto.ClienteRequestDto;
import com.electrodostore.cliente_service.dto.ClienteResponseDto;

import java.util.List;

//Intefaz declarativa de las operaciones que hace el dominio Cliente
public interface IClienteService {

    //Traer todos
    List<ClienteResponseDto> findAllClientes();

    //Encontrar uno
    ClienteResponseDto findClienteResponse(Long id);

    //Guardar registro
    ClienteResponseDto saveCliente(ClienteRequestDto newClient);

    //Eliminar por id
    void deleteCliente(Long id);

    //Actualización completa
    ClienteResponseDto updateCliente(Long id, ClienteRequestDto updatedClient);

    //Actualización parcial
    ClienteResponseDto patchCliente(Long id, ClienteRequestDto updatedClient);
}
