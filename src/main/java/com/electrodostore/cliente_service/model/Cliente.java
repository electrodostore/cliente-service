package com.electrodostore.cliente_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter  @Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity  //Entidad Cliente para registro de clientes de la tienda
public class Cliente {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cellphone;
    private String document;
    private String address;

}
