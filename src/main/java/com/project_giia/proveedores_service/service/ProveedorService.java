package com.project_giia.proveedores_service.service;

import com.project_giia.proveedores_service.clients.DataManagementClient;
import com.project_giia.proveedores_service.events.ProveedorEventPublisher;
import com.project_giia.proveedores_service.entity.Proveedor;
import com.project_giia.proveedores_service.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service

public class ProveedorService {

	private final ProveedorRepository proveedorRepository;
    private final ProveedorEventPublisher eventPublisher;
    private final DataManagementClient dataManagementClient;
    private final ReactiveRedisTemplate<String, Proveedor> redisTemplate;

    @Value("${datacache.redis.key-prefix.proveedor}")
    private String keyPrefix;
    @Value("${datacache.redis.channel.proveedor}")
    private String channel;

    public ProveedorService(ProveedorRepository proveedorRepository, ProveedorEventPublisher eventPublisher, DataManagementClient dataManagementClient, ReactiveRedisTemplate<String, Proveedor> redisTemplate) {
        this.proveedorRepository = proveedorRepository;
        this.eventPublisher = eventPublisher;
        this.dataManagementClient=dataManagementClient;
        this.redisTemplate=redisTemplate;
    }

    public Mono<Proveedor> crear(Proveedor proveedor) {
        return proveedorRepository.save(proveedor)
                .flatMap(saved ->
                        // Publicamos en Redis después de guardar
                        eventPublisher.publishProveedorCreated(channel,"Crear")
                                .thenReturn(saved) // devolvemos el proveedor al cliente
                )
                .doOnError(e -> System.err.println(" Error creando proveedor: " + e.getMessage()));
    }

    public Flux<Proveedor> listar() {
        return redisTemplate.keys(keyPrefix + "*")
                .flatMap(key -> redisTemplate.opsForValue()
                        .get(key)
                        .cast(Proveedor.class));
    }

    public Mono<Proveedor> obtener(Long id) {
        String redisKey = keyPrefix + id;
        return redisTemplate.opsForValue()
                .get(redisKey)
                .cast(Proveedor.class);
    }

    public Mono<Proveedor> actualizar(Long id, Proveedor datos) {
        return proveedorRepository.findById(id)
                .flatMap(p -> {
                    p.setNit(datos.getNit());
                    p.setNombre(datos.getNombre());
                    p.setEmail(datos.getEmail());
                    p.setTelefono(datos.getTelefono());
                    p.setDireccion(datos.getDireccion());
                    return proveedorRepository.save(p).flatMap(saved ->
                                    eventPublisher.publishProveedorCreated(channel, "Actualizar")
                                    .thenReturn(saved))
                                    .doOnError(e -> System.err.println(" Error actualizando proveedor: " + e.getMessage()));
                });
    }

    public Mono<Proveedor> desactivar(Long id) {
        return proveedorRepository.findById(id)
                .flatMap(p -> {
                    p.setActivo("0");
                    return proveedorRepository.save(p).flatMap(saved ->
                                                eventPublisher.publishProveedorCreated(channel, "Desactivar")
                                                .thenReturn(saved))
                                                .doOnError(e -> System.err.println(" Error a proveedor: " + e.getMessage()));
                });
    }
}

