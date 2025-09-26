package com.project_giia.proveedores_service;


import static org.mockito.ArgumentMatchers.any;

class UsuarioProveedorServiceTest {
/*
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

 */
}
