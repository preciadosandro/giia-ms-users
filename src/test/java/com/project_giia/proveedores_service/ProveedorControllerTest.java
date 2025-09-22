package com.project_giia.proveedores_service;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.project_giia.proveedores_service.controller.ProveedorController;
import com.project_giia.proveedores_service.entity.Proveedor;
import com.project_giia.proveedores_service.service.ProveedorService;

import reactor.core.publisher.Mono;

class ProveedorControllerTest {

    private ProveedorService proveedorService;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        proveedorService = Mockito.mock(ProveedorService.class);
        ProveedorController controller = new ProveedorController(proveedorService);

        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void testCrearProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setNit("888888");
        proveedor.setNombre("Proveedor Test");
        proveedor.setActivo(true);
        proveedor.setFechaRegistro(LocalDateTime.now());

        Mockito.when(proveedorService.crear(any(Proveedor.class)))
                .thenReturn(Mono.just(proveedor));

        webTestClient.post().uri("/api/proveedores")
                .bodyValue(proveedor)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("Proveedor Test");
    }
}
