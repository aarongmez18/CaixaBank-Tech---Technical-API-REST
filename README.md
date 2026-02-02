# CaixaBank Tech – Technical API REST

API REST desarrollada con **Spring Boot** para la gestión de solicitudes de préstamos personales.  
Permite crear solicitudes, consultar préstamos existentes y modificar su estado siguiendo un flujo de negocio definido.

El proyecto utiliza **JPA con base de datos en memoria H2**, **MapStruct** para el mapeo entre entidades y DTOs, y validaciones para garantizar la coherencia de los estados.

El proyecto está orientado a ser una solución sencilla, clara y fácil de ejecutar, pensada como prueba técnica.

---

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- MapStruct
- Swagger / OpenAPI
- SLF4J
- Maven

---

---

## Requisitos previos

- Java 17 o superior
- Maven 3.8+
- Git

---

## 1. Instalación del proyecto

Clonar el repositorio y acceder al directorio raíz:

```bash

git clone https://github.com/aarongmez18/CaixaBank-Tech---Technical-API-REST.git

cd CaixaBank-Tech---Technical-API-REST
```

## 2. Ejecución de la aplicación
```bash

 mvn spring-boot:run
```

La aplicación arrancará en:
```bash

http://localhost:8080/caixabank
```
## 3. Endpoints disponibles

### Comprobar si la aplicación está levantada
Permite comprobar que la aplicación está en funcionamiento.

```bash

GET /health
```

### Crear solicitud de préstamo

Crea una nueva solicitud de préstamo personal.

```bash

POST /loans
```

### Listar solicitudes

Obtiene el listado completo de solicitudes.

```bash

GET /loans
```

### Filtro por estado

Permite filtrar las solicitudes por su estado actual.

```bash

GET /loans?status=PENDING
```

Estados disponibles:

* PENDING – Solicitud pendiente
* APPROVED – Solicitud aprobada
* REJECTED – Solicitud rechazada
* CANCELLED – Solicitud cancelada

### Consultar solicitud por id

Obtiene el detalle de una solicitud concreta.

```bash

GET /loans/{id}
```
### Modificar estado de una solicitud

Actualiza el estado de una solicitud existente respetando el flujo de estados definido.

```bash

PATCH /loans/status/{id}
```

Flujo permitido:

* PENDING → APPROVED o REJECTED
* APPROVED → CANCELLED

## 4. Documentación Swagger

La API está documentada mediante Swagger, lo que permite probar los endpoints de forma interactiva.

```bash

http://localhost:8080/caixabank/swagger
```

## 5. Base de datos H2

La aplicación utiliza una base de datos H2 en memoria para facilitar la ejecución sin dependencias externas.

```bash

http://localhost:8080/caixabank/h2-console

```

## 6. Arquitectura y decisiones técnicas

La aplicación sigue una arquitectura en capas bien definida:

* Controller: Exposición de endpoints REST y validación de peticiones.
* Service: Contiene la lógica de negocio, incluyendo la validación del flujo de estados permitido.
* Repository: Acceso a datos mediante Spring Data JPA.
* DTO: Separación entre el modelo interno y los datos expuestos por la API.
* Mapper: Conversión entre entidades y DTOs para evitar mapeos manuales repetitivos.
* Exception:Uso de excepciones personalizadas y códigos HTTP adecuados.
* Logging: Uso de SLF4J para trazabilidad básica de las operaciones.

## 8. Mejoras y extensiones futuras

Con más tiempo, se podrían implementar las siguientes mejoras:

### Funcionales

* Gestión de usuarios (clientes y gestores).
* Paginación y ordenación de resultados.
* Nuevas entidades relacionadas (clientes, contratos, pagos).

### Técnicas

* Persistencia en base de datos relacional (PostgreSQL o Oracle).
* Autenticación y autorización mediante JWT / OAuth2.
* Dockerización del proyecto.
* Separación en microservicios.
* Tests unitarios y de integración.
* Pipeline de integración y despliegue continuo (CI/CD).


Aarón Gómez Delgado. 2026 &copy; 
