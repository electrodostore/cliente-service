package com.electrodostore.cliente_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter  @Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cellphone;

    //El documento de un usuario debe ser único
    @Column(unique = true)
    private String document;

    private String address;

    //Estado del cliente
    private boolean active=true;
}
