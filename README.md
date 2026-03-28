# 👤 Cliente Service

## 📌 Descripción
Microservicio encargado de la gestión de clientes dentro de ElectrodoStore. Permite registrar, consultar, actualizar y eliminar clientes, además de integrar información de ventas asociadas a cada cliente.

Forma parte de una arquitectura de microservicios basada en Spring Cloud.

---

## ⚙️ Tecnologías utilizadas

- Java + Spring Boot
- Spring Data JPA
- MySQL
- Spring Cloud (Eureka Client)
- OpenFeign (comunicación entre servicios)
- Circuit-Breaker (Resiliencia entre microservicios)

---

## 🧩 Responsabilidades

- Registrar nuevos clientes
- Consultar clientes
- Obtener detalle de un cliente
- Actualizar información de clientes
- Eliminar clientes
- Consultar ventas asociadas a un cliente

---

## 🗄️ Base de datos

Este servicio maneja su propia base de datos MySQL, siguiendo el patrón **Database per Service**, lo que garantiza independencia y desacoplamiento respecto a otros microservicios.

---

## 🔗 Endpoints principales

```http
GET    /clientes
GET    /clientes/{id}
GET    /clientes/traer-ventas/{clientId}
POST   /clientes
PUT    /clientes/{id}
PATCH  /clientes/{id}
DELETE /clientes/{id}
```
---

## 🔄 Integración con otros servicios

Este microservicio se integra con:

- **💳 venta-service** → para obtener las ventas asociadas a un cliente

La comunicación se realiza mediante Spring Cloud OpenFeign, permitiendo invocar otros servicios de forma declarativa.

---

## ⚠️ Manejo de errores

El servicio implementa un manejo centralizado de excepciones utilizando @RestControllerAdvice.

### Excepciones manejadas:
- **ClienteNotFoundException** → cuando un cliente no existe
- **ServiceUnavailable** → cuando un servicio externo no responde

### Estructura de respuesta de error:

```bash
{
  "timestamp": "2026-03-28T12:00:00",
  "status": 404,
  "error": "NOT_FOUND",
  "errorCode": "CLIENT_NOT_FOUND",
  "mensaje": "Cliente no encontrado"
}
```
Esto garantiza respuestas consistentes y facilita el manejo de errores en el frontend o en otros microservicios.

---

## 🌐 Registro en Eureka

El servicio se registra automáticamente en Eureka Server, permitiendo su descubrimiento dinámico dentro del ecosistema de microservicios.

---

## ▶️ Ejecución local

- **Con Maven**
```bash
# Corre en el puerto 8080
mvn spring-boot:run
```
- **Con Docker**
```bash
docker build -t cliente-service .
```

---

## 🔌 Configuración de red

| Propiedad | Valor                  |
|---|------------------------|
| Puerto interno | `8080`                 |
| Acceso externo | ❌ Solo vía API Gateway |

---

## 🎯 Flujo destacado

**Obtener cliente con sus ventas**
1. Se consulta el cliente por ID
2. Se realiza una llamada a venta-service
3. Se agregan las ventas al DTO de respuesta
4. Se retorna la información consolidada

---

## 🛡️ Resiliencia (Circuit Breaker + Retry)

La comunicación con **venta-service** está protegida mediante patrones de resiliencia utilizando **Resilience4j**:

- **Circuit Breaker** → Evita llamadas repetidas a un servicio caído
- **Retry** → Reintenta automáticamente en fallos transitorios
- **Fallback** → Proporciona una respuesta controlada en caso de error

### 🔁 Flujo de resiliencia

1. Se intenta consumir el servicio de ventas
2. Si falla, se realizan reintentos automáticos
3. Si el fallo persiste, se activa el **Circuit Breaker**
4. Se ejecuta el método **fallback**
5. Se lanza una excepción controlada (`ServiceUnavailable`)

### ⚠️ Fallback

Cuando el servicio de ventas no está disponible:

- Se registra un log de advertencia
- Se lanza una excepción de tipo infraestructura
- Se evita propagar errores internos al cliente

---

## 🚀 Mejoras futuras

- Implementación de autenticación (JWT / OAuth2)
- Validaciones más robustas
- Paginación en consultas

---