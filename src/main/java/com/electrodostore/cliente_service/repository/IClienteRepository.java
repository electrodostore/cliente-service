package com.electrodostore.cliente_service.repository;

import com.electrodostore.cliente_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/***
 * Repositorio de datos para cliente-service
 */
@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
}
