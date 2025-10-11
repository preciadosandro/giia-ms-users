package com.project_giia.proveedores_service.controller;

import com.project_giia.proveedores_service.dtos.Login;
import com.project_giia.proveedores_service.dtos.LoginResponse;
import com.project_giia.proveedores_service.entity.Usuario;
import com.project_giia.proveedores_service.service.UsuarioProveedorService;
import com.project_giia.proveedores_service.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios Proveedores", description = "Gestión y autenticación de usuarios de proveedores")
@RequiredArgsConstructor
public class UsuarioProveedorController {

    private final UsuarioProveedorService usuarioProveedorService;
    private final UsuarioService usuarioService;


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


    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista con todos los usuarios registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<Flux<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }


    @Operation(summary = "Obtener usuario por ID", description = "Devuelve la información de un usuario específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Mono<Usuario>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }


    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario proveedor en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creado correctamente",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Mono<Usuario>> create(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.create(usuario));
    }


    @Operation(summary = "Actualizar usuario existente", description = "Permite modificar los datos de un usuario ya existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Mono<Usuario>> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.update(id, usuario));
    }


    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario proveedor por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Usuario>> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok( usuarioService.desactivar(id));
    }
}
