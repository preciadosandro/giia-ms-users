package com.project_giia.proveedores_service.service;

import com.project_giia.proveedores_service.dtos.Login;

import com.project_giia.proveedores_service.repository.ProveedorRepository;
import com.project_giia.proveedores_service.repository.UsuarioProveedorRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service

public class UsuarioProveedorService {

    private final UsuarioProveedorRepository usuarioProveedorRepository;

    private final ProveedorRepository proveedorRepository;

    public UsuarioProveedorService(UsuarioProveedorRepository usuarioProveedorRepository, ProveedorRepository proveedorRepository) {
        this.usuarioProveedorRepository = usuarioProveedorRepository;
        this.proveedorRepository= proveedorRepository;
    }

    public Mono<Boolean> vincularUsuarioAdmin(Login login) {
    return usuarioProveedorRepository.findByUsuario(login.getUser()).flatMap(usuario -> {
        if(usuario.getPasswordHash().equals(login.getPassword())){
            return Mono.just(true);
        }
        return Mono.just(false);
    });
    }

    public Mono<Boolean> vincularUsuario(Login login) {
        return proveedorRepository.findByUsuarioProv(login.getUser()).flatMap(usuario -> {
            if(usuario.getPasswordHash().equals(login.getPassword())){
                return Mono.just(true);
            }
            return Mono.just(false);
        });
    }




}
