📦 Proveedores Service

Microservicio de gestión de proveedores y sus usuarios asociados, construido con Spring Boot WebFlux, SQL Server y Redis.
Forma parte del ecosistema GIIA.

⚙️ Características principales

✔️ CRUD completo de proveedores.
✔️ Gestión de usuarios vinculados a proveedores (vía auth-service).
✔️ Publicación de eventos en Redis tras la creación de proveedores.
✔️ Arquitectura reactiva con Mono y Flux.

🛠️ Tecnologías utilizadas

Java 17

Spring Boot 3

Spring WebFlux (reactivo)

Spring Data R2DBC (SQL Server)

Spring Data Redis Reactive

Docker / Docker Compose

Maven

📂 Estructura de la base de datos

Tabla: Proveedores

CREATE TABLE Proveedores (
  ID INT PRIMARY KEY IDENTITY,
  nit VARCHAR(50),
  nombre VARCHAR(255),
  email VARCHAR(255),
  telefono VARCHAR(50),
  direccion VARCHAR(255),
  activo BIT,
  fecha_registro DATETIME2
);


Tabla: Usuario_Proveedores

CREATE TABLE Usuario_Proveedores (
  ID INT PRIMARY KEY IDENTITY,
  usuario_id INT, -- Referencia a auth-service
  proveedor_id INT FOREIGN KEY REFERENCES Proveedores(ID),
  fecha_asignacion DATETIME2,
  es_principal BIT
);

🌐 Endpoints principales
📌 Gestión de Proveedores

POST /api/proveedores → Crear nuevo proveedor

GET /api/proveedores → Listar proveedores

GET /api/proveedores/{id} → Obtener proveedor por ID

PUT /api/proveedores/{id} → Actualizar proveedor

DELETE /api/proveedores/{id} → Desactivar proveedor

📌 Gestión de Usuarios Proveedores

POST /api/proveedores/{id}/usuarios → Vincular usuario a proveedor

GET /api/proveedores/{id}/usuarios → Listar usuarios de un proveedor

DELETE /api/proveedores/{id}/usuarios/{usuarioId} → Desvincular usuario

🚀 Cómo levantar el microservicio
1. Compilar el proyecto
mvn clean package -DskipTests


Generará el .jar en target/proveedores-service-0.0.1-SNAPSHOT.jar.
