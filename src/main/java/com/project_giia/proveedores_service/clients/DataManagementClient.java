package com.project_giia.proveedores_service.clients;

import com.project_giia.proveedores_service.entity.Proveedor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DataManagementClient {

    private final WebClient webClient;

    public DataManagementClient( @Value("${datacache.url}") String baseUrl)
                                  {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Obtener todos los proveedores
     * GET {{baseURL}}/proveedores/all
     */
    public Flux<Proveedor> getAllProveedores() {
        return webClient.get()
                .uri("/proveedores/all")
                .retrieve()
                .bodyToFlux(Proveedor.class);
    }

    /**
     * Obtener proveedor por ID
     * GET {{baseURL}}/proveedores/{id}
     */
    public Mono<Proveedor> getProveedorById(Long id) {
        return webClient.get()
                .uri("/proveedores/{id}", id)
                .retrieve()
                .bodyToMono(Proveedor.class);
    }
}
