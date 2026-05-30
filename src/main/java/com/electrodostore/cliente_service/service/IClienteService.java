package com.electrodostore.cliente_service.service;

import com.electrodostore.cliente_service.dto.ClienteConVentasDto;
import com.electrodostore.cliente_service.dto.ClientePatchRequestDto;
import com.electrodostore.cliente_service.dto.ClienteRequestDto;
import com.electrodostore.cliente_service.dto.ClienteResponseDto;

import java.util.List;

public interface IClienteService {

    /**
     * Consulta todos los clientes en un contexto administrativo
     */
    List<ClienteResponseDto> findAllClientes();

    /**
     * Consulta administrativa de un cliente
     */
    ClienteResponseDto findClienteResponse(Long id);

    /**
     * Consulta operacional de clientes activos
     */
    ClienteResponseDto findActiveClient(Long id);

    Long saveCliente(ClienteRequestDto newClient);

    /**
     * Se aplica el borrado lógico desactivando al cliente para
     * evitar perder datos históricos importantes.
     */
    void disableCliente(Long id);

    /**
     * Actualización administrativa total.
     */
    ClienteResponseDto updateCliente(Long id, ClienteRequestDto updatedClient);

    /**
     * Actualización administrativa parcial.
     */
    ClienteResponseDto patchCliente(Long id, ClientePatchRequestDto updatedClient);

    /**
     * Expone ventas y datos de un determinado cliente.
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
