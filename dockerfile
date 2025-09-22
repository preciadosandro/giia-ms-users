# Imagen base de Java 17 (Spring Boot 3 usa 17 por defecto)
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR generado al contenedor
COPY target/proveedores-service-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto del microservicio (según application.yml -> 8082)
EXPOSE 8082

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]