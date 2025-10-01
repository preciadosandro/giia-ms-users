package com.project_giia.proveedores_service.dtos;

import lombok.Data;

@Data
public class ProveedorRequest {
    // Usuario
    private String usuario;
    private String nombreUsuario;
    private String emailUsuario;
    private String passwordHash;

    // Proveedor
    private String nit;
    private String nombreProveedor;
    private String emailProveedor;
    private String telefono;
    private String direccion;
}