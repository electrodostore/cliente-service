package com.electrodostore.cliente_service.service;

import com.electrodostore.cliente_service.Integration.venta.dto.VentaDto;
import com.electrodostore.cliente_service.dto.ClienteConVentasDto;
import com.electrodostore.cliente_service.dto.ClienteRequestDto;
import com.electrodostore.cliente_service.dto.ClienteResponseDto;

import java.util.List;

//Interfaz declarativa de las operaciones que hace el dominio Cliente
public interface IClienteService {

    //Traer todos
    List<ClienteResponseDto> findAllClientes();

    //Encontrar uno
    ClienteResponseDto findClienteResponse(Long id);

    //Guardar registro
    ClienteResponseDto saveCliente(ClienteRequestDto newClient);

    //Eliminar por ID
    void deleteCliente(Long id);

    //Actualización completa
    ClienteResponseDto updateCliente(Long id, ClienteRequestDto updatedClient);

    //Actualización parcial
    ClienteResponseDto patchCliente(Long id, ClienteRequestDto updatedClient);

    //Método de exposición de ventas de un determinado cliente y los datos de este mismo
    ClienteConVentasDto findClienteVentas(Long clientId);
}
