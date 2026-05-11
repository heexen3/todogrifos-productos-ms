# 🛠️ TodoGrifos - Arquitectura de Microservicios

## 📌 Descripción del Proyecto

**TodoGrifos** es una aplicación backend basada en arquitectura de microservicios que simula la gestión de productos, inventario y operaciones comerciales de una tienda especializada en grifería.

El sistema está diseñado bajo principios de **arquitectura distribuida**, permitiendo escalabilidad, desacoplamiento y mantenibilidad mediante múltiples microservicios independientes.

---

## 🎯 Objetivo

Desarrollar una solución completa utilizando:

* Spring Boot
* Arquitectura de Microservicios
* JPA + Hibernate
* Validaciones con Bean Validation
* Manejo centralizado de excepciones
* Comunicación entre servicios
* API Gateway y Service Discovery

---

## 🧱 Arquitectura

El sistema se compone de:

### 🔧 Infraestructura

* **Eureka Server** → Registro y descubrimiento de servicios
* **API Gateway** → Punto de entrada único para clientes

### 🧩 Microservicios de negocio (en desarrollo)

* productos-ms ✅
* inventario-ms 🚧
* pedidos-ms 🚧
* usuarios-ms 🚧
* proveedores-ms 🚧
* categorias-ms 🚧
* marcas-ms 🚧
* pagos-ms 🚧
* envios-ms 🚧
* notificaciones-ms 🚧
* reportes-ms 🚧

---

## 🧪 Tecnologías utilizadas

* Java 17+
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Spring Cloud (Eureka, Gateway)
* Maven
* Postman (testing)

---

## 📂 Estructura del proyecto

Cada microservicio sigue el patrón **CSR (Controller - Service - Repository)**:

```
controller  → manejo de endpoints REST
service     → lógica de negocio
repository  → acceso a datos
model       → entidades JPA
dto         → transferencia de datos
exception   → manejo de errores
```

---

## 🔄 Funcionalidades implementadas (productos-ms)

* CRUD completo de productos
* Validación de datos con DTOs
* Manejo de excepciones personalizadas
* Conversión Entity ↔ DTO
* Filtros:

  * Buscar por nombre
  * Buscar por SKU
  * Filtrar por marca
  * Filtrar por categoría
  * Listar productos activos

---

## ⚠️ Manejo de errores

Se implementa manejo centralizado mediante:

* `@ControllerAdvice`
* Excepciones personalizadas:

  * ProductoNotFoundException
  * SkuDuplicadoException
  * CategoriaNotFoundException
  * MarcaNotFoundException

---

## 🔗 Comunicación entre microservicios

(En desarrollo)

Se utilizará:

* WebClient o Feign Client
* Consumo de endpoints entre servicios
* Manejo de errores y timeouts

---

## ▶️ Ejecución del proyecto

### 1. Levantar Eureka Server

```
http://localhost:8761
```

### 2. Levantar microservicios

Cada microservicio se registra automáticamente en Eureka.

### 3. Acceso mediante API Gateway

```
http://localhost:8080/api/productos
```

---

## 🧪 Testing

Las pruebas se realizan con Postman:

* Tests positivos (200, 201)
* Tests negativos (404, 400)
* Validación de errores
* Colecciones reutilizables por microservicio

---

## 👨‍💻 Integrantes

* Nombre Apellido
* Nombre Apellido

---

## 📌 Estado del proyecto

🚧 En desarrollo - Evaluación Parcial 2

---

## 📈 Consideraciones

* Arquitectura desacoplada y escalable
* Separación de responsabilidades clara
* Buenas prácticas REST
* Código limpio y mantenible

---
