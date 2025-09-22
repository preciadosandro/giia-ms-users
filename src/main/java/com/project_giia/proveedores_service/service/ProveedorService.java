package com.project_giia.proveedores_service.service;

import com.project_giia.proveedores_service.events.ProveedorEventPublisher;
import com.project_giia.proveedores_service.entity.Proveedor;
import com.project_giia.proveedores_service.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProveedorService {

	private final ProveedorRepository proveedorRepository;
    private final ProveedorEventPublisher eventPublisher;

    public ProveedorService(ProveedorRepository proveedorRepository, ProveedorEventPublisher eventPublisher) {
        this.proveedorRepository = proveedorRepository;
        this.eventPublisher = eventPublisher;
    }

    public Mono<Proveedor> crear(Proveedor proveedor) {
        proveedor.setActivo(true);
        proveedor.setFechaRegistro(LocalDateTime.now());

        return proveedorRepository.save(proveedor)
                .flatMap(saved ->
                        // Publicamos en Redis después de guardar
                        eventPublisher.publishProveedorCreated(saved)
                                .thenReturn(saved) // devolvemos el proveedor al cliente
                )
                .doOnError(e -> System.err.println("❌ Error creando proveedor: " + e.getMessage()));
    }

    public Flux<Proveedor> listar() {
        return proveedorRepository.findAll();
    }

    public Mono<Proveedor> obtener(Long id) {
        return proveedorRepository.findById(id);
    }

    public Mono<Proveedor> actualizar(Long id, Proveedor datos) {
        return proveedorRepository.findById(id)
                .flatMap(p -> {
                    p.setNit(datos.getNit());
                    p.setNombre(datos.getNombre());
                    p.setEmail(datos.getEmail());
                    p.setTelefono(datos.getTelefono());
                    p.setDireccion(datos.getDireccion());
                    return proveedorRepository.save(p);
                });
    }

    public Mono<Proveedor> desactivar(Long id) {
        return proveedorRepository.findById(id)
                .flatMap(p -> {
                    p.setActivo(false);
                    return proveedorRepository.save(p);
                });
    }
}

