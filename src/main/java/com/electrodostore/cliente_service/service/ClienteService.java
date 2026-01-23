package com.electrodostore.cliente_service.service;

import com.electrodostore.cliente_service.dto.ClienteRequestDto;
import com.electrodostore.cliente_service.dto.ClienteResponseDto;
import com.electrodostore.cliente_service.exception.ClienteNotFoundException;
import com.electrodostore.cliente_service.model.Cliente;
import com.electrodostore.cliente_service.repository.IClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService{

    private final IClienteRepository clienteRepo;

    //Inyección de dependenica por constructor para el repository de cliente
    public ClienteService(IClienteRepository clienteRepo){this.clienteRepo = clienteRepo;}

    //Método propio para sacar una instancia de la clase ClienteResponse que es la que se expone al cliente (view)
    private ClienteResponseDto buildClienteResponse(Cliente objCliente){
        return new ClienteResponseDto(objCliente.getId(), objCliente.getName(), objCliente.getCellphone(),
                objCliente.getDocument(), objCliente.getAddress());
    }

    //Método propio para buscar a un cliente por su id o throw exception en caso de que no exista
    private Cliente findCliente(Long id){
        //Guardamos el objeto Cliente en un Optional para evitar problemas con el nullPointExceptiton
        Optional<Cliente> objCliente = clienteRepo.findById(id);

        //Si el objecto Optional está vacío -> Excepción personalizada
        if(objCliente.isEmpty()){throw new ClienteNotFoundException("No se encontró cliente con id: " + id);}

        return objCliente.get();
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

    @Override
    @Transactional
    public ClienteResponseDto saveCliente(ClienteRequestDto newClient) {
        Cliente objCliente = new Cliente();

        //Migramos datos del dto al objeto de la clase entidad
        objCliente.setName(newClient.getName());
        objCliente.setCellphone(newClient.getCellphone());
        objCliente.setDocument(newClient.getDocument());
        objCliente.setAddress(newClient.getAddress());

        //Se guarda el registro
        clienteRepo.save(objCliente);

        return buildClienteResponse(objCliente);
    }

    @Override
    @Transactional
    public void deleteCliente(Long id) {
        //Si no existe cliente, lo sabremos gracias a esta busqueda
        Cliente objCliente = findCliente(id);

        clienteRepo.delete(objCliente);
    }

    @Override
    @Transactional
    public ClienteResponseDto updateCliente(Long id, ClienteRequestDto updatedClient) {
        Cliente objCliente = findCliente(id);

        //Actualización completa de los datos del cliente
        objCliente.setName(updatedClient.getName());
        objCliente.setCellphone(updatedClient.getCellphone());
        objCliente.setDocument(updatedClient.getDocument());
        objCliente.setAddress(updatedClient.getAddress());

        //Se guardan los cambios
        clienteRepo.save(objCliente);

        return buildClienteResponse(objCliente);
    }

    @Override
    @Transactional
    public ClienteResponseDto patchCliente(Long id, ClienteRequestDto updatedClient) {
        Cliente objCliente = findCliente(id);

        //Actualización parcial de los datos del cliente
        if(updatedClient.getName() != null){objCliente.setName(updatedClient.getName());}
        if(updatedClient.getCellphone() != null){objCliente.setCellphone(updatedClient.getCellphone());}
        if(updatedClient.getDocument() != null){objCliente.setDocument(updatedClient.getDocument());}
        if(updatedClient.getAddress() != null){objCliente.setAddress(updatedClient.getAddress());}

        //Se guardan los cambios
        clienteRepo.save(objCliente);

        return buildClienteResponse(objCliente);
    }
}
