package com.project_giia.proveedores_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Proveedores")
                        .version("1.0.0")
                        .description("Microservicio para la gestión de proveedores.")
                        .contact(new Contact()
                                .name("Proyecto GIIA")
                                .email("soporte@giia.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
