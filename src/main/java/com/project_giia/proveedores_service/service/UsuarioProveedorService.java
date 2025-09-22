package com.project_giia.proveedores_service.service;

import com.project_giia.proveedores_service.entity.UsuarioProveedor;
import com.project_giia.proveedores_service.repository.UsuarioProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioProveedorService {

    private final UsuarioProveedorRepository usuarioProveedorRepository;
    
    public UsuarioProveedorService(UsuarioProveedorRepository usuarioProveedorRepository) {
        this.usuarioProveedorRepository = usuarioProveedorRepository;
    }

    public Mono<UsuarioProveedor> vincularUsuario(Long proveedorId, Long usuarioId, Boolean esPrincipal) {
        UsuarioProveedor usuarioProveedor = new UsuarioProveedor();
        usuarioProveedor.setProveedorId(proveedorId);
        usuarioProveedor.setUsuarioId(usuarioId);
        usuarioProveedor.setEsPrincipal(esPrincipal);
        usuarioProveedor.setFechaAsignacion(LocalDateTime.now());

        return usuarioProveedorRepository.save(usuarioProveedor);
    }

    public Flux<UsuarioProveedor> listarUsuarios(Long proveedorId) {
        return usuarioProveedorRepository.findByProveedorId(proveedorId);
    }

    public Mono<Void> desvincularUsuario(Long proveedorId, Long usuarioId) {
        return usuarioProveedorRepository.findByProveedorId(proveedorId)
                .filter(up -> up.getUsuarioId().equals(usuarioId))
                .next()
                .flatMap(usuarioProveedorRepository::delete);
    }
}
