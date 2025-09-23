package com.project_giia.proveedores_service.repository;

import com.project_giia.proveedores_service.entity.Proveedor;
import com.project_giia.proveedores_service.entity.Usuario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProveedorRepository extends ReactiveCrudRepository<Proveedor, Long> {
    Mono<Usuario> findByUsuarioProv(String user);
}
