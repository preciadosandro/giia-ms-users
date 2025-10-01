package com.project_giia.proveedores_service.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Usuario")
public class Usuario {

    @Id
    private Long id;

    @Column("usuario")
    private String usuario;

    @Column("nombre")
    private String nombre;

    @Column("email")
    private String email;

    @Column("password_hash")
    private String passwordHash;

    @Column("rol_id")
    private Integer rolId;

    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column("activo")
    private Boolean activo;

}