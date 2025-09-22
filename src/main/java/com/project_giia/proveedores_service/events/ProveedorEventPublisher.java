package com.project_giia.proveedores_service.events;

import com.project_giia.proveedores_service.entity.Proveedor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProveedorEventPublisher {

    private final ReactiveRedisTemplate<String, Object> redisTemplate;

    public ProveedorEventPublisher(ReactiveRedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Mono<Long> publishProveedorCreated(Proveedor proveedor) {
        String channel = "proveedores:created:" + proveedor.getId(); // canal único por proveedor
        return redisTemplate.convertAndSend(channel, proveedor)
                .doOnNext(count -> System.out.println("📢 Evento publicado en Redis: " + channel));
    }
}
