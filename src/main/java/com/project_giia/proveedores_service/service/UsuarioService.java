package com.project_giia.proveedores_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_giia.proveedores_service.entity.Proveedor;
import com.project_giia.proveedores_service.entity.Usuario;
import com.project_giia.proveedores_service.events.ProveedorEventPublisher;
import com.project_giia.proveedores_service.repository.UsuarioProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioProveedorRepository repository;
    private final ProveedorEventPublisher eventPublisher;
    @Value("${datacache.redis.key-prefix.usuario-proveedor}")
    private String keyPrefix;
    @Value("${datacache.redis.channel.usuario-proveedor}")
    private String channel;

    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public Flux<Usuario> findAll() {
        return redisTemplate.keys(keyPrefix + "*")
                .flatMap(key -> redisTemplate.opsForValue()
                        .get(key)
                        .map(json -> {
                            try {
                                return objectMapper.readValue(json, Usuario.class);
                            } catch (Exception e) {
                                throw new RuntimeException("Error deserializando Proveedor", e);
                            }
                        })
                );
    }

    public Mono<Usuario> findById(Long id) {
        String redisKey = keyPrefix + id;
        return redisTemplate.opsForValue()
                .get(redisKey)
                .map(json-> {
                    try {
                        return objectMapper.readValue(json, Usuario.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Error deserializando Proveedor", e);
                    }
                });
    }

    public Mono<Usuario> create(Usuario usuario) {
        usuario.setActivo(true);
        return repository.save(usuario).flatMap(saved ->
                eventPublisher.publishProveedorCreated(channel, "Crear Usuario")
                        .thenReturn(saved) // devolvemos el proveedor al cliente
        );
    }

    public Mono<Usuario> update(Long id, Usuario usuario) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setUsuario(usuario.getUsuario());
                    existing.setNombre(usuario.getNombre());
                    existing.setEmail(usuario.getEmail());
                    existing.setPasswordHash(usuario.getPasswordHash());
                    existing.setRolId(usuario.getRolId());
                    existing.setActivo(usuario.getActivo());
                    return repository.save(existing);
                }).flatMap(saved ->
                        eventPublisher.publishProveedorCreated(channel, "Actualizar Usuario")
                                .thenReturn(saved))
                .doOnError(e -> System.err.println(" Error actualizando proveedor: " + e.getMessage()));
    }

    public Mono<Usuario>  desactivar(Long id) {
        return repository.findById(id)
                .flatMap(p -> {
                    p.setActivo(false);
                    return repository.save(p).flatMap(saved ->
                                    eventPublisher.publishProveedorCreated(channel, "Desactivar Usuario")
                                            .thenReturn(saved))
                            .doOnError(e -> System.err.println(" Error a proveedor: " + e.getMessage()));
                });
    }
}