package com.project_giia.proveedores_service.controller;

import com.project_giia.proveedores_service.dtos.Login;
import com.project_giia.proveedores_service.dtos.LoginResponse;
import com.project_giia.proveedores_service.service.UsuarioProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios Proveedores", description = "Gestión y autenticación de usuarios de proveedores")
public class UsuarioProveedorController {

    private final UsuarioProveedorService usuarioProveedorService;

    public UsuarioProveedorController(UsuarioProveedorService usuarioProveedorService) {
        this.usuarioProveedorService = usuarioProveedorService;
    }

    @Operation(
            summary = "Iniciar sesión de proveedor",
            description = "Permite que un usuario proveedor se autentique en el sistema usando sus credenciales."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content),
            @ApiResponse(responseCode = "400", description = "Solicitud mal formada", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<Mono<LoginResponse>> loginProveedor(@RequestBody Login login) {
        return ResponseEntity.ok(usuarioProveedorService.vincularUsuario(login));
    }
}
