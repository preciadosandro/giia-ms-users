package com.project_giia.proveedores_service;

import com.project_giia.proveedores_service.entity.UsuarioProveedor;
import com.project_giia.proveedores_service.repository.UsuarioProveedorRepository;
import com.project_giia.proveedores_service.service.UsuarioProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UsuarioProveedorServiceTest {

    private UsuarioProveedorRepository usuarioProveedorRepository;
    private UsuarioProveedorService usuarioProveedorService;

    @BeforeEach
    void setUp() {
        usuarioProveedorRepository = Mockito.mock(UsuarioProveedorRepository.class);
        usuarioProveedorService = new UsuarioProveedorService(usuarioProveedorRepository);
    }

    @Test
    void testVincularUsuario() {
        UsuarioProveedor usuarioProveedor = new UsuarioProveedor();
        usuarioProveedor.setId(1L);
        usuarioProveedor.setProveedorId(10L);
        usuarioProveedor.setUsuarioId(20L);
        usuarioProveedor.setEsPrincipal(true);
        usuarioProveedor.setFechaAsignacion(LocalDateTime.now());

        when(usuarioProveedorRepository.save(any(UsuarioProveedor.class)))
                .thenReturn(Mono.just(usuarioProveedor));

        Mono<UsuarioProveedor> result = usuarioProveedorService.vincularUsuario(10L, 20L, true);

        StepVerifier.create(result)
                .expectNextMatches(up -> up.getProveedorId().equals(10L) && up.getUsuarioId().equals(20L))
                .verifyComplete();
    }
}
