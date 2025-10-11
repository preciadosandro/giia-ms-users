package com.project_giia.proveedores_service.controller;

import com.project_giia.proveedores_service.dtos.ProveedorRequest;
import com.project_giia.proveedores_service.entity.Proveedor;
import com.project_giia.proveedores_service.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/proveedores")
@Tag(name = "Proveedores", description = "Gestión de proveedores y sus usuarios asociados")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
		this.proveedorService = proveedorService;
	}

    @Operation(summary = "Crear un nuevo proveedor", description = "Registra un nuevo proveedor con sus datos básicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Proveedor.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public Mono<Proveedor> crear(@RequestBody ProveedorRequest proveedor) {
        return proveedorService.crear(proveedor);
    }

    @Operation(summary = "Listar todos los proveedores", description = "Devuelve una lista completa de los proveedores registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
            content = @Content(schema = @Schema(implementation = Proveedor.class)))
    @GetMapping
    public Flux<Proveedor> listar() {
        return proveedorService.listar();
    }

    @Operation(summary = "Obtener un proveedor por ID", description = "Devuelve los detalles de un proveedor específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado",
                    content = @Content(schema = @Schema(implementation = Proveedor.class))),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public Mono<Proveedor> obtener(
            @Parameter(description = "ID del proveedor a buscar", required = true)
            @PathVariable Long id) {
        return proveedorService.obtener(id);
    }

    @Operation(summary = "Actualizar un proveedor", description = "Permite modificar los datos de un proveedor existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = Proveedor.class))),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public Mono<Proveedor> actualizar(
            @Parameter(description = "ID del proveedor a actualizar", required = true)
            @PathVariable Long id,
            @RequestBody Proveedor proveedor) {
        return proveedorService.actualizar(id, proveedor);
    }

    @Operation(summary = "Desactivar un proveedor", description = "Marca un proveedor como inactivo sin eliminarlo de la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor desactivado correctamente",
                    content = @Content(schema = @Schema(implementation = Proveedor.class))),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public Mono<Proveedor> desactivar(
            @Parameter(description = "ID del proveedor a desactivar", required = true)
            @PathVariable Long id) {
        return proveedorService.desactivar(id);
    }
}
