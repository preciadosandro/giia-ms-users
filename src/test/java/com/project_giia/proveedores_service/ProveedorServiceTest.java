package com.project_giia.proveedores_service;

import com.project_giia.proveedores_service.entity.Proveedor;
import com.project_giia.proveedores_service.repository.ProveedorRepository;
import com.project_giia.proveedores_service.service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProveedorServiceTest {
/*
    private ProveedorRepository proveedorRepository;
    private ProveedorService proveedorService;

    @BeforeEach
    void setUp() {
        proveedorRepository = Mockito.mock(ProveedorRepository.class);
        proveedorService = new ProveedorService(proveedorRepository);
    }

    @Test
    void testCrearProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setNit("888888");
        proveedor.setNombre("Proveedor Test");
        proveedor.setActivo(true);
        proveedor.setFechaRegistro(LocalDateTime.now());

        when(proveedorRepository.save(any(Proveedor.class)))
                .thenReturn(Mono.just(proveedor));

        Mono<Proveedor> result = proveedorService.crear(proveedor);

        StepVerifier.create(result)
                .expectNextMatches(p -> p.getNombre().equals("Proveedor Test"))
                .verifyComplete();
    }

 */
}
