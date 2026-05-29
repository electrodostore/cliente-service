package com.electrodostore.cliente_service.service;

import com.electrodostore.cliente_service.Integration.venta.dto.VentaDto;
import com.electrodostore.cliente_service.dto.ClienteConVentasDto;
import com.electrodostore.cliente_service.dto.ClientePatchRequestDto;
import com.electrodostore.cliente_service.dto.ClienteRequestDto;
import com.electrodostore.cliente_service.dto.ClienteResponseDto;

import java.util.List;

//Interfaz declarativa de las operaciones que hace el dominio Cliente
public interface IClienteService {

    //Consulta todos los clientes en un contexto administrativo
    List<ClienteResponseDto> findAllClientes();

    //Consulta administrativa de un cliente
    ClienteResponseDto findClienteResponse(Long id);

    //Consulta operacional de clientes activos
    ClienteResponseDto findActiveClient(Long id);

    //Guardar registro
    Long saveCliente(ClienteRequestDto newClient);

    //Se aplica Soft Delete para evitar perder datos históricos del usuario una vez se desactive
    void disableCliente(Long id);

    //Actualización completa administrativa
    ClienteResponseDto updateCliente(Long id, ClienteRequestDto updatedClient);

    //Actualización parcial administrativa
    ClienteResponseDto patchCliente(Long id, ClientePatchRequestDto updatedClient);

    /**
     * Expone ventas de un determinado cliente y los datos de este mismo.
     *
     * Este método solo podrá ser accedido por administradores del sistema
     */
    ClienteConVentasDto findClienteVentas(Long clientId);

    /**
     * Gestión de identidad del cliente.
     *
     * Solo se accederá a los recursos del cliente autenticado para evitar
     * que usuarios no autorizado accedan o modifiquen recursos
     * de otros clientes
     */
    ClienteResponseDto findMe();
    ClienteResponseDto updateMe(ClienteRequestDto updatedClient);
    ClienteResponseDto patchMe(ClientePatchRequestDto updatedClient);
    ClienteConVentasDto findMyVentas();
}
