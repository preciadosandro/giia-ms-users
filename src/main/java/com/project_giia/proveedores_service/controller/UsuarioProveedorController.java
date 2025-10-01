package com.project_giia.proveedores_service.controller;

import com.project_giia.proveedores_service.dtos.Login;
import com.project_giia.proveedores_service.dtos.LoginResponse;
import com.project_giia.proveedores_service.entity.Proveedor;
import com.project_giia.proveedores_service.service.UsuarioProveedorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioProveedorController {

    private final UsuarioProveedorService usuarioProveedorService;
    
    public UsuarioProveedorController(UsuarioProveedorService usuarioProveedorService) {
		this.usuarioProveedorService = usuarioProveedorService;
	}


    @PostMapping("/login")
    public ResponseEntity<Mono<LoginResponse>> loginProvedor(@RequestBody Login login){

        return  ResponseEntity.ok(usuarioProveedorService.vincularUsuario(login));
    }



}
