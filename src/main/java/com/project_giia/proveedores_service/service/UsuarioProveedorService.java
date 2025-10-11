package com.project_giia.proveedores_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_giia.proveedores_service.dtos.Login;

import com.project_giia.proveedores_service.dtos.LoginResponse;
import com.project_giia.proveedores_service.entity.Proveedor;
import com.project_giia.proveedores_service.entity.Usuario;
import com.project_giia.proveedores_service.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service

public class UsuarioProveedorService {

    @Value("${datacache.redis.key-prefix.usuario-proveedor}")
    private String keyPrefix;
    private final ObjectMapper objectMapper;
    private final ProveedorRepository proveedorRepository;
    private final ReactiveRedisTemplate<String, String> redisTemplate;

    public UsuarioProveedorService( ProveedorRepository proveedorRepository, ReactiveRedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.proveedorRepository= proveedorRepository;
        this.redisTemplate=redisTemplate;
        this.objectMapper=objectMapper;
    }

    public Mono<LoginResponse> vincularUsuario(Login login) {
        String redisKey = keyPrefix + login.getUser();
        return redisTemplate.opsForValue()
                .get(redisKey)
                .flatMap(  json -> {
                    Usuario usuario= null;
                    try {
                        usuario = objectMapper.readValue(json, Usuario.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Error deserializando Usuario", e);
                    }
                    if (usuario != null && usuario.getPasswordHash().equals(login.getPassword()) && usuario.getActivo()) {

                        return Mono.just(LoginResponse.builder()
                                .rol(usuario.getRolId())
                                .signIn(true)
                                .build());
                    } else {

                        return Mono.just(LoginResponse.builder()
                                .rol(0)
                                .signIn(false)
                                .build());
                    }
                })
                .switchIfEmpty(Mono.just(LoginResponse.builder()
                        .rol(0)
                        .signIn(false)
                        .build()));
    }






}
