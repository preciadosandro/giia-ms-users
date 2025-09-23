package com.project_giia.proveedores_service.dtos;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class ProvedorRequest {


    private String usuario_prov;
    private String password;
    private String nit;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String activo;

}
