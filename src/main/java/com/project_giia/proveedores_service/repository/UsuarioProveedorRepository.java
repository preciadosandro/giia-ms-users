package com.project_giia.proveedores_service.repository;

import com.project_giia.proveedores_service.entity.UsuarioProveedor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UsuarioProveedorRepository extends ReactiveCrudRepository<UsuarioProveedor, Long> {
    Flux<UsuarioProveedor> findByProveedorId(Long proveedorId);
}