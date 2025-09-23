package com.project_giia.proveedores_service.controller;

import com.project_giia.proveedores_service.entity.Proveedor;
import com.project_giia.proveedores_service.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
		this.proveedorService = proveedorService;
	}

	@PostMapping
    public Mono<Proveedor> crear(@RequestBody Proveedor proveedor) {
        return proveedorService.crear(proveedor);
    }

    @GetMapping
    public Flux<Proveedor> listar() {
        return proveedorService.listar();
    }

    @GetMapping("/{id}")
    public Mono<Proveedor> obtener(@PathVariable Long id) {
        return proveedorService.obtener(id);
    }

    @PutMapping("/{id}")
    public Mono<Proveedor> actualizar(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        return proveedorService.actualizar(id, proveedor);
    }

    @DeleteMapping("/{id}")
    public Mono<Proveedor> desactivar(@PathVariable Long id) {
        return proveedorService.desactivar(id);
    }
}