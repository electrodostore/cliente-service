package com.electrodostore.cliente_service.service;

import com.electrodostore.cliente_service.Integration.venta.VentaIntegrationService;
import com.electrodostore.cliente_service.Integration.venta.dto.VentaDto;
import com.electrodostore.cliente_service.dto.ClienteConVentasDto;
import com.electrodostore.cliente_service.dto.ClientePatchRequestDto;
import com.electrodostore.cliente_service.dto.ClienteRequestDto;
import com.electrodostore.cliente_service.dto.ClienteResponseDto;
import com.electrodostore.cliente_service.exception.ClienteNotFoundException;
import com.electrodostore.cliente_service.exception.UnauthorizedOperationException;
import com.electrodostore.cliente_service.model.Cliente;
import com.electrodostore.cliente_service.repository.IClienteRepository;
import feign.Client;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService{

    //Inyección de dependencia para el repository de cliente
    private final IClienteRepository clienteRepo;
    //Inyección de dependencia para el cliente de venta-service
    private final VentaIntegrationService ventaClient;
    //Inyección por constructor
    public ClienteService(IClienteRepository clienteRepo, VentaIntegrationService ventaClient){
        this.clienteRepo = clienteRepo;
        this.ventaClient = ventaClient;
    }

    //Método propio para sacar una instancia de la clase ClienteResponse que es la que se expone al cliente (view)
    private ClienteResponseDto buildClienteResponse(Cliente objCliente){
        return new ClienteResponseDto(
                objCliente.getId(),
                objCliente.getName(),
                objCliente.getCellphone(),
                objCliente.getDocument(),
                objCliente.getAddress(),
                objCliente.isActive()
        );
    }

    //Método propio para buscar a un cliente por su id o throw exception en caso de que no exista
    private Cliente findCliente(Long id){
        //Guardamos el objeto Cliente en un Optional para evitar problemas con el nullPointExceptiton
        Optional<Cliente> objCliente = clienteRepo.findById(id);

        //Si el objecto Optional está vacío -> Excepción personalizada
        if(objCliente.isEmpty()){throw new ClienteNotFoundException("No se encontró cliente con id: " + id);}

        return objCliente.get();
    }

    private void validarEstadoCliente(Cliente cliente){
        if(!cliente.isActive()){
            throw new ClienteNotFoundException("Cliente inactivo, no puede realizar la operación");
        }
    }

    //Extrae la identidad de cliente autenticado y retorna su id
    private Long getAuthenticatedClientId(){
        //Busca objeto con la información del token JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //Saca objeto Principal con todos los claims almacenamos en el token
        Jwt principal = (Jwt) authentication.getPrincipal();

        //Busca identidad de negocio del usuario
        Number clientId = principal.getClaim("clientId");

        //Valida que el usuario realmente sea cliente
        if(clientId == null){throw new UnauthorizedOperationException("El usuario no es cliente, por lo que " +
                "no puede realizar la operación");
        }

        return  clientId.longValue();

    }

    private void validarNotBlank(String valor, String nombreCampo){
        //Valida que no se obtenga un String vacío
        if(valor.isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, nombreCampo + " no puede estar vacío"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDto> findAllClientes() {
        List<ClienteResponseDto> listClientes = new ArrayList<>();

        //Se va preparando uno a uno a los clientes para ser expuestos a la vista
        for(Cliente objCliente: clienteRepo.findAll()){
            listClientes.add(buildClienteResponse(objCliente));
        }

        return listClientes;
    }

    //Se expone el cliente a la vista pero en modo Response
    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDto findClienteResponse(Long id) {
        return buildClienteResponse(
                //Este método me busca un determinado cliente por su id, si no existe -> Exception
                findCliente(id)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ClienteResponseDto findActiveClient(Long id) {
        Cliente cliente = findCliente(
                id
        );

        validarEstadoCliente(cliente);

        return buildClienteResponse(cliente);
    }

    @Override
    @Transactional
    public Long saveCliente(ClienteRequestDto newClient) {
        Cliente objCliente = new Cliente();

        //Migramos datos del dto al objeto de la clase entidad
        objCliente.setName(newClient.name());
        objCliente.setCellphone(newClient.cellphone());
        objCliente.setDocument(newClient.document());
        objCliente.setAddress(newClient.address());

        //Se guarda el registro
        return clienteRepo.save(objCliente).getId();

    }

    @Override
    @Transactional
    public void disableCliente(Long id) {
        Cliente objCliente = findCliente(id);

        //Borrado lógico
        objCliente.setActive(false);
    }

    @Override
    @Transactional
    public ClienteResponseDto updateCliente(Long id, ClienteRequestDto updatedClient) {
        Cliente objCliente = findCliente(id);

        validarEstadoCliente(objCliente);

        //Actualización completa de los datos del cliente
        objCliente.setName(updatedClient.name());
        objCliente.setCellphone(updatedClient.cellphone());
        objCliente.setDocument(updatedClient.document());
        objCliente.setAddress(updatedClient.address());

        //Se guardan los cambios
        clienteRepo.save(objCliente);

        return buildClienteResponse(objCliente);
    }

    @Override
    @Transactional
    public ClienteResponseDto patchCliente(Long id, ClientePatchRequestDto updatedClient) {
        Cliente objCliente = findCliente(id);

        validarEstadoCliente(objCliente);

        //Actualización parcial de los datos del cliente
        if(updatedClient.name() != null){
            validarNotBlank(updatedClient.name(), "el nombre");
            objCliente.setName(updatedClient.name());
        }

        if(updatedClient.cellphone() != null){
            validarNotBlank(updatedClient.cellphone(), "el teléfono");
            objCliente.setCellphone(updatedClient.cellphone());
        }

        if(updatedClient.document() != null){
            validarNotBlank(updatedClient.document(), "el documento");
            objCliente.setDocument(updatedClient.document());
        }

        if(updatedClient.address() != null){
            validarNotBlank(updatedClient.address(), "la dirección");
            objCliente.setAddress(updatedClient.address());
        }

        return buildClienteResponse(objCliente);
    }

    @Transactional(readOnly = true)
    @Override
    public ClienteConVentasDto findClienteVentas(Long clientId) {
        //Traemos al cliente dueño de las ventas
        Cliente objCliente = findCliente(clientId);

        //Buscamos ventas del cliente
        List<VentaDto> listVentas = ventaClient.findClienteVentas(clientId);

        //Retornamos objeto de integración del cliente y sus ventas
        return new ClienteConVentasDto(
                listVentas,
                buildClienteResponse(objCliente)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ClienteResponseDto findMe() {
        //Busca cliente autenticado
        Cliente cliente = findCliente(
                getAuthenticatedClientId()
        );

        return buildClienteResponse(
                cliente
        );
    }

    @Transactional
    @Override
    public ClienteResponseDto updateMe(ClienteRequestDto updatedClient) {
        return updateCliente(
                getAuthenticatedClientId(), updatedClient
        );
    }

    @Transactional
    @Override
    public ClienteResponseDto patchMe(ClientePatchRequestDto updatedClient) {
        return patchCliente(
                getAuthenticatedClientId(), updatedClient
        );
    }

    @Transactional(readOnly = true)
    @Override
    public ClienteConVentasDto findMyVentas() {
        return findClienteVentas(
                getAuthenticatedClientId()
        );
    }
}
