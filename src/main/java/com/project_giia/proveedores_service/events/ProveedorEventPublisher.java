package com.project_giia.proveedores_service.events;

import com.project_giia.proveedores_service.entity.Proveedor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ProveedorEventPublisher {

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    public ProveedorEventPublisher(ReactiveRedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Mono<Long> publishProveedorCreated(String channel, String value) {
        return redisTemplate.convertAndSend(channel, value)
                .doOnNext(count -> System.out.println(" Evento publicado en Redis channel: " + channel));
    }
}
