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
    @Value("${datacache.redis.key-prefix.proveedor}")
        private String keyPrefixProvedor;
    private final ObjectMapper objectMapper;
    private final ProveedorRepository proveedorRepository;
    private final ReactiveRedisTemplate<String, String> redisTemplate;

    public UsuarioProveedorService( ProveedorRepository proveedorRepository, ReactiveRedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.proveedorRepository= proveedorRepository;
        this.redisTemplate=redisTemplate;
        this.objectMapper=objectMapper;
    }

    public Mono<LoginResponse> vincularUsuario(Login login) {
        String redisKey = keyPrefix + "*";

        return redisTemplate.keys(keyPrefix + "*")
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, Usuario.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Error deserializando Remision", e);
                    }
                })
                .filter(usu -> usu.getUsuario().equals(login.getUser()))
                .next()
                .flatMap(  usuario -> {
                    if (usuario != null && usuario.getPasswordHash().equals(login.getPassword()) && usuario.getActivo()) {
                         return redisTemplate.keys(keyPrefixProvedor + "*")
                                .flatMap(key -> redisTemplate.opsForValue()
                                        .get(key)
                                        .map(json -> {
                                            try {
                                                return objectMapper.readValue(json, Proveedor.class);
                                            } catch (Exception e) {
                                                throw new RuntimeException("Error deserializando Proveedor", e);
                                            }
                                        })
                                )
                                 .filter(pfilter-> pfilter.getIdUsuario().equals(usuario.getId()))
                                 .next()
                                 .flatMap(p-> Mono.just(LoginResponse.builder()
                                         .rol(usuario.getRolId())
                                         .signIn(true)
                                         .idUsuario(usuario.getId())
                                                 .idProvedor(p.getId())
                                         .build()));
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
