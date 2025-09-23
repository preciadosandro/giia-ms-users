package com.project_giia.proveedores_service.events;

import com.project_giia.proveedores_service.entity.Proveedor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ProveedorEventPublisher {

    private final ReactiveRedisTemplate<String, Object> redisTemplate;

    public ProveedorEventPublisher(ReactiveRedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Mono<Long> publishProveedorCreated() {
        Map<String, String> eventMessage = Map.of("type", "PROVEEDOR");
        String channel = "proveedor-updates"; // canal único por proveedor
        return redisTemplate.convertAndSend(channel, eventMessage)
                .doOnNext(count -> System.out.println("📢 Evento publicado en Redis: " + channel));
    }
}
