package com.project_giia.proveedores_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_giia.proveedores_service.dtos.ProveedorRequest;
import com.project_giia.proveedores_service.entity.Usuario;
import com.project_giia.proveedores_service.events.ProveedorEventPublisher;
import com.project_giia.proveedores_service.entity.Proveedor;
import com.project_giia.proveedores_service.repository.ProveedorRepository;
import com.project_giia.proveedores_service.repository.UsuarioProveedorRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProveedorService {

	private final ProveedorRepository proveedorRepository;
    private final UsuarioProveedorRepository usuarioProveedorRepository;
    private final ProveedorEventPublisher eventPublisher;
    private final ReactiveRedisTemplate<String, String> redisTemplate;

    @Value("${datacache.redis.key-prefix.proveedor}")
    private String keyPrefix;
    @Value("${datacache.redis.channel.proveedor}")
    private String channel;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public ProveedorService(ProveedorRepository proveedorRepository, ProveedorEventPublisher eventPublisher, ReactiveRedisTemplate<String, String> redisTemplate, UsuarioProveedorRepository usuarioProveedorRepository) {
        this.proveedorRepository = proveedorRepository;
        this.eventPublisher = eventPublisher;
        this.redisTemplate=redisTemplate;
        this.usuarioProveedorRepository=usuarioProveedorRepository;
    }

    @Transactional
    public Mono<Proveedor> crear(ProveedorRequest proveedor) {
        Usuario usuario = Usuario.builder()
                .usuario(proveedor.getUsuario())
                .nombre(proveedor.getNombreUsuario())
                .email(proveedor.getEmailUsuario())
                .passwordHash(proveedor.getPasswordHash())
                .rolId(2)
                .activo(true)
                .build();
        return usuarioProveedorRepository.save(usuario)
                .flatMap(usu -> {
                    Proveedor proveedorDb = Proveedor.builder()
                            .nit(proveedor.getNit())
                            .nombre(proveedor.getNombreProveedor())
                            .email(proveedor.getEmailProveedor())
                            .telefono(proveedor.getTelefono())
                            .direccion(proveedor.getDireccion())
                            .activo("1")
                            .idUsuario(usu.getId())
                            .build();
                    return proveedorRepository.save(proveedorDb)
                            .flatMap(saved ->
                                    eventPublisher.publishProveedorCreated(channel, "Crear")
                                            .thenReturn(saved) // devolvemos el proveedor al cliente
                            );
                })
                .doOnError(e -> log.error("Error creando proveedor: " + e.getMessage()));
    }

    public Flux<Proveedor> listar() {
        return redisTemplate.keys(keyPrefix + "*")
                .flatMap(key -> redisTemplate.opsForValue()
                        .get(key)
                        .map(json -> {
                            try {
                                return objectMapper.readValue(json, Proveedor.class);
                            } catch (Exception e) {
                                throw new RuntimeException("Error deserializando Proveedor", e);
                            }
                        })
                );
    }

    public Mono<Proveedor> obtener(Long id) {
        String redisKey = keyPrefix + id;
        return redisTemplate.opsForValue()
                .get(redisKey)
                .map(json-> {
                    try {
                        return objectMapper.readValue(json, Proveedor.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Error deserializando Proveedor", e);
                    }
                });
    }
    @Transactional
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
                                    .thenReturn(saved));

                })
                .doOnError(e -> System.err.println(" Error actualizando proveedor: " + e.getMessage()));
    }
    @Transactional
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

