package com.project_giia.proveedores_service.repository;

import com.project_giia.proveedores_service.entity.Proveedor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProveedorRepository extends ReactiveCrudRepository<Proveedor, Long> {
}
