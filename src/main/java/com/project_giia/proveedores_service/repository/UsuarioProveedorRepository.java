package com.project_giia.proveedores_service.repository;

import com.project_giia.proveedores_service.entity.Usuario;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioProveedorRepository extends ReactiveCrudRepository<Usuario, Long> {
    Mono<Usuario> findByUsuario(String user);
}