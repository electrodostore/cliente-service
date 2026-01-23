package com.electrodostore.cliente_service.repository;

import com.electrodostore.cliente_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Interfaz que hereda de la clase de Jpa que nos ofrece una gran cantidad de métodos disponibles para nuestras operaciones HTTP
@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
}
