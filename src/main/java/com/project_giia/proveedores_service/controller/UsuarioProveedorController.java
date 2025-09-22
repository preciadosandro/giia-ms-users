package com.project_giia.proveedores_service.controller;

import com.project_giia.proveedores_service.entity.UsuarioProveedor;
import com.project_giia.proveedores_service.service.UsuarioProveedorService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/proveedores/{proveedorId}/usuarios")
@RequiredArgsConstructor
public class UsuarioProveedorController {

    private final UsuarioProveedorService usuarioProveedorService;
    
    public UsuarioProveedorController(UsuarioProveedorService usuarioProveedorService) {
		this.usuarioProveedorService = usuarioProveedorService;
	}

    @PostMapping
    public Mono<UsuarioProveedor> vincular(@PathVariable Long proveedorId,
                                           @RequestParam Long usuarioId,
                                           @RequestParam(defaultValue = "false") Boolean esPrincipal) {
        return usuarioProveedorService.vincularUsuario(proveedorId, usuarioId, esPrincipal);
    }

    @GetMapping
    public Flux<UsuarioProveedor> listar(@PathVariable Long proveedorId) {
        return usuarioProveedorService.listarUsuarios(proveedorId);
    }

    @DeleteMapping("/{usuarioId}")
    public Mono<Void> desvincular(@PathVariable Long proveedorId, @PathVariable Long usuarioId) {
        return usuarioProveedorService.desvincularUsuario(proveedorId, usuarioId);
    }
}
