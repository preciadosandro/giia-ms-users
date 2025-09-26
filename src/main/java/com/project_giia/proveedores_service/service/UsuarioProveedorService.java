package com.project_giia.proveedores_service.service;

import com.project_giia.proveedores_service.dtos.Login;

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
    private String keyPrefixProv;

    private final ProveedorRepository proveedorRepository;
    private final ReactiveRedisTemplate<String, Object> redisTemplate;

    public UsuarioProveedorService( ProveedorRepository proveedorRepository, ReactiveRedisTemplate<String, Object> redisTemplate) {
        this.proveedorRepository= proveedorRepository;
        this.redisTemplate=redisTemplate;
    }

    public Mono<Boolean> vincularUsuarioAdmin(Login login) {
        String redisKey = keyPrefix + login.getUser();
        return redisTemplate.opsForValue()
                .get(redisKey)
                .cast(Usuario.class)
                .flatMap(  usuario -> {
                    if (usuario != null && usuario.getPasswordHash().equals(login.getPassword())) {
                        return Mono.just(true);
                    } else {
                        return Mono.just(false);
                    }
                })
                .switchIfEmpty(Mono.just(false));
    }

    public Mono<Boolean> vincularUsuario(Login login) {
        String redisKey = keyPrefixProv + login.getUser();
        return redisTemplate.opsForValue()
                .get(redisKey)
                .cast(Proveedor.class)
                .flatMap(  usuario -> {
                    if (usuario != null && usuario.getPassword().equals(login.getPassword())) {
                        return Mono.just(true);
                    } else {
                        return Mono.just(false);
                    }
                })
                .switchIfEmpty(Mono.just(false));
    }




}
