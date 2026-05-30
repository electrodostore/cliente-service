package com.electrodostore.cliente_service.service;

import com.electrodostore.cliente_service.Integration.venta.VentaIntegrationService;
import com.electrodostore.cliente_service.Integration.venta.dto.VentaIntegrationDto;
import com.electrodostore.cliente_service.dto.ClienteConVentasDto;
import com.electrodostore.cliente_service.dto.ClientePatchRequestDto;
import com.electrodostore.cliente_service.dto.ClienteRequestDto;
import com.electrodostore.cliente_service.dto.ClienteResponseDto;
import com.electrodostore.cliente_service.exception.ClienteNotFoundException;
import com.electrodostore.cliente_service.exception.UnauthorizedOperationException;
import com.electrodostore.cliente_service.model.Cliente;
import com.electrodostore.cliente_service.repository.IClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService implements IClienteService{

    private final IClienteRepository clienteRepo;
    private final VentaIntegrationService ventaClient;

    public ClienteService(IClienteRepository clienteRepo, VentaIntegrationService ventaClient){
        this.clienteRepo = clienteRepo;
        this.ventaClient = ventaClient;
    }

    /**
     * Construye DTO de respuesta para exponer un cliente.
     */
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

    /**
     * Busca cliente por su id o lanza excepción si no existe.
     */
    private Cliente findCliente(Long id){
        return clienteRepo.findById(id)
                .orElseThrow(
                () -> new ClienteNotFoundException("No se encontró cliente con id: " + id)
        );
    }

    private void validarEstadoCliente(Cliente cliente){
        if(!cliente.isActive()){
            throw new ClienteNotFoundException("Cliente inactivo, no puede realizar la operación");
        }
    }

    /**
     * Extrae la identidad de cliente autenticado y retorna su id
     */
    private Long getAuthenticatedClientId(){
        //Obtiene autenticación actual del usuario.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //Recupera claims almacenados en el JWT
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

        for(Cliente objCliente: clienteRepo.findAll()){
            listClientes.add(
                    buildClienteResponse(objCliente)
            );
        }

        return listClientes;
    }

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
        Cliente cliente = findCliente(id);

        validarEstadoCliente(cliente);

        return buildClienteResponse(cliente);
    }

    @Transactional
    @Override
    public Long saveCliente(ClienteRequestDto newClient) {
        Cliente objCliente = new Cliente();

        objCliente.setName(newClient.name());
        objCliente.setCellphone(newClient.cellphone());
        objCliente.setDocument(newClient.document());
        objCliente.setAddress(newClient.address());

        return clienteRepo.save(objCliente).getId();

    }

    @Transactional
    @Override
    public void disableCliente(Long id) {
        Cliente objCliente = findCliente(id);

        //Borrado lógico
        objCliente.setActive(false);
    }

    @Transactional
    @Override
    public ClienteResponseDto updateCliente(Long id, ClienteRequestDto updatedClient) {
        Cliente objCliente = findCliente(id);

        //Actualización completa de los datos del cliente
        objCliente.setName(updatedClient.name());
        objCliente.setCellphone(updatedClient.cellphone());
        objCliente.setDocument(updatedClient.document());
        objCliente.setAddress(updatedClient.address());

        return buildClienteResponse(objCliente);
    }

    @Override
    @Transactional
    public ClienteResponseDto patchCliente(Long id, ClientePatchRequestDto updatedClient) {
        Cliente objCliente = findCliente(id);

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
        Cliente objCliente = findCliente(clientId);

        //Busca ventas del cliente en venta-service
        List<VentaIntegrationDto> listVentas = ventaClient.findClienteVentas(clientId);

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
