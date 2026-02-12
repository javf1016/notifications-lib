# 📢 Notifications Library – Arquitectura Enterprise

![Java](https://img.shields.io/badge/Java-21-orange)
![Architecture](https://img.shields.io/badge/Architecture-Clean%20%26%20Hexagonal-blue)
![Patterns](https://img.shields.io/badge/Design%20Patterns-Enterprise-purple)
![Status](https://img.shields.io/badge/Status-Production%20Ready-success)
![License](https://img.shields.io/badge/License-MIT-green)

> 💡 **Resumen ejecutivo:**
> Esta librería implementa un motor de notificaciones extensible y resiliente, diseñado con principios de arquitectura empresarial. Permite soportar múltiples canales y proveedores, aplicando patrones de resiliencia, eventos de dominio y un pipeline configurable. La solución está pensada como base para sistemas distribuidos modernos.

---

## 📑 Tabla de contenidos

* [📌 Descripción del proyecto](#-descripción-del-proyecto)
* [🎯 Objetivos de diseño](#-objetivos-de-diseño)
* [🧩 Alcance funcional](#-alcance-funcional)
* [⚙️ Requisitos funcionales y no funcionales](#️-requisitos-funcionales-y-no-funcionales)
* [🏗️ Arquitectura del sistema](#️-arquitectura-del-sistema)
* [🧠 Patrones y principios aplicados](#-patrones-y-principios-aplicados)
* [📡 Canales y proveedores](#-canales-y-proveedores)
* [🔄 Flujo de ejecución](#-flujo-de-ejecución)
* [🚀 Ejemplo de uso](#-ejemplo-de-uso)
* [🛡️ Estrategia de resiliencia](#️-estrategia-de-resiliencia)
* [📢 Eventos de dominio](#-eventos-de-dominio)
* [🧪 Estrategia de pruebas](#-estrategia-de-pruebas)
* [🧠 Decisiones arquitectónicas](#-decisiones-arquitectónicas)
* [⚖️ Trade-offs y limitaciones](#️-trade-offs-y-limitaciones)
* [🔮 Roadmap y mejoras futuras](#-roadmap-y-mejoras-futuras)
* [▶️ Ejecución del proyecto](#️-ejecución-del-proyecto)
* [📊 Matriz de requisitos vs implementación](#-matriz-de-requisitos-vs-implementación)
* [🧾 Conclusión](#-conclusión)

---

## 📌 Descripción del proyecto

La **Notifications Library** es una librería diseñada para resolver el problema de envío de notificaciones en sistemas modernos, donde se requiere:

* Soporte multi-canal (Email, SMS, Push).
* Alta disponibilidad mediante múltiples proveedores.
* Resiliencia ante fallos.
* Extensibilidad sin modificar el core.
* Observabilidad mediante eventos.

El diseño está basado en **Clean Architecture** y **Arquitectura Hexagonal**, permitiendo desacoplar el dominio de la infraestructura y facilitar la evolución del sistema.

---

## 🎯 Objetivos de diseño

* **Extensibilidad:** agregar nuevos canales o proveedores sin modificar el core.
* **Resiliencia:** tolerancia a fallos mediante patrones de diseño.
* **Mantenibilidad:** separación clara de responsabilidades.
* **Escalabilidad:** preparado para crecimiento funcional.
* **Testabilidad:** lógica de negocio desacoplada de infraestructura.

---

## 🧩 Alcance funcional

La librería permite:

* Enviar notificaciones por múltiples canales.
* Configurar múltiples proveedores con fallback automático.
* Ejecutar un pipeline configurable de procesamiento.
* Aplicar políticas de retry.
* Publicar eventos de dominio.
* Ejecutar notificaciones de forma síncrona o asíncrona.

---

## ⚙️ Requisitos funcionales y no funcionales

### Requisitos funcionales (RF)

* RF1: Enviar notificaciones por Email, SMS y Push.
* RF2: Soportar múltiples proveedores por canal.
* RF3: Implementar fallback entre proveedores.
* RF4: Implementar retry ante fallos.
* RF5: Publicar eventos de dominio.
* RF6: Permitir extensión de canales y proveedores.

### Requisitos no funcionales (RNF)

* RNF1: Alta cohesión y bajo acoplamiento.
* RNF2: Cumplimiento de principios SOLID.
* RNF3: Arquitectura extensible y modular.
* RNF4: Resiliencia ante fallos.
* RNF5: Alta testabilidad.

---

## 🏗️ Arquitectura del sistema

### 🔝 Visión de alto nivel

```text
Cliente
 → Use Case (Caso de Uso)
  → Pipeline de ejecución
    → Validación
    → Resolución de canal
    → Ejecución del canal
      → Cadena de proveedores (fallback)
        → Rate Limiter
        → Circuit Breaker
        → Provider real
    → Retry
    → Publicación de eventos
```

### 🧱 Arquitectura por capas (Clean Architecture)

```text
Domain
 ├── model
 ├── event
 └── ports

Application
 ├── usecase
 └── pipeline

Infrastructure
 ├── channel
 ├── provider
 ├── resilience
 ├── async
 ├── event
 └── config

Demo
```

---

## 🧠 Patrones y principios aplicados

| Categoría      | Patrón / Principio                         |
| -------------- | ------------------------------------------ |
| Arquitectura   | Clean Architecture, Hexagonal Architecture |
| Diseño OO      | SOLID, OCP, DIP                            |
| Comportamiento | Strategy, Chain of Responsibility          |
| Estructurales  | Decorator                                  |
| Orquestación   | Pipeline Pattern                           |
| Resiliencia    | Retry, Circuit Breaker, Rate Limiter       |
| Integración    | Domain Events                              |

---

## 📡 Canales y proveedores

| Canal | Proveedores                      |
| ----- | -------------------------------- |
| Email | SendGrid, FailingProvider (demo) |
| SMS   | Twilio                           |
| Push  | Firebase                         |

Cada canal soporta múltiples proveedores con fallback automático.

---

## 🔄 Flujo de ejecución

1. El cliente solicita el envío de una notificación.
2. El caso de uso crea un contexto de ejecución.
3. El pipeline ejecuta los pasos definidos:

   * Validación.
   * Resolución dinámica del canal.
   * Ejecución con fallback de proveedores.
   * Retry en caso de fallo.
   * Publicación de eventos de dominio.
4. Se retorna el resultado al cliente.

---

## 🚀 Ejemplo de uso

```java
var client = NotificationConfiguration.defaultClient();

var resultMail = client.send(
        ChannelType.EMAIL,
        new Notification("jorge@test.com", "Hello", "Mensaje de prueba")
);

var resultSms = client.send(
        ChannelType.SMS,
        new Notification("3187949964", "SMS", "Hello Test SMS")
);

var resultPush = client.send(
        ChannelType.PUSH,
        new Notification("device-id-123", "Push", "Hello Test Push")
);

System.out.println(resultMail);
System.out.println(resultSms);
System.out.println(resultPush);
```

### Salida esperada

```text
Failing Email Provider
Sending Email via SendGrid to jorge@test.com
EVENT -> NotificationSentEvent
Sending SMS via Twilio to 3187949964
EVENT -> NotificationSentEvent
Sending Push Firebase to device-id-123
EVENT -> NotificationSentEvent
```

---

## 🛡️ Estrategia de resiliencia

* **Retry:** reintentos configurables ante fallos.
* **Circuit Breaker:** aislamiento de proveedores inestables.
* **Rate Limiter:** control de tráfico.
* **Fallback:** conmutación automática entre proveedores.

---

## 📢 Eventos de dominio

Eventos publicados:

* `NotificationSentEvent`
* `NotificationFailedEvent`

Los eventos se gestionan mediante un Event Bus desacoplado y extensible.

---

## 🧪 Estrategia de pruebas

* Tests unitarios de casos de uso.
* Tests de fallback y retry.
* Tests de resolución de canales.

Ejemplo:

```java
assertTrue(result.isSuccess());
```

---

## 🧠 Decisiones arquitectónicas

* Separación estricta entre dominio, aplicación e infraestructura.
* Uso de interfaces para desacoplar dependencias.
* Pipeline extensible sin modificar el core.
* Decoradores para resiliencia.
* Diseño orientado a eventos.

---

## ⚖️ Trade-offs y limitaciones

* Event Bus en memoria (no distribuido).
* Configuración embebida.
* Persistencia de eventos no implementada.

---

## 🔮 Roadmap y mejoras futuras

* Event Bus distribuido (Kafka / RabbitMQ).
* Observabilidad (Prometheus, OpenTelemetry).
* Estrategias avanzadas de retry.

---

## ▶️ Ejecución del proyecto

### Requisitos

* Java 21
* Maven o Gradle

### Ejecución

```bash
mvn clean install
java -cp target/classes com.company.notifications.demo.DemoApp
```

---

## 📊 Matriz de requisitos vs implementación

| Requisito       | Implementación                 |
| --------------- | ------------------------------ |
| Multi-canal     | Strategy Pattern (Channel)     |
| Multi-proveedor | Lista de Providers + Fallback  |
| Retry           | RetryPolicy + Pipeline         |
| Resiliencia     | Circuit Breaker + Rate Limiter |
| Extensibilidad  | Clean Architecture             |
| Observabilidad  | Domain Events                  |

---

## 🧾 Conclusión

Esta librería demuestra un diseño de arquitectura empresarial para un sistema de notificaciones. La solución combina principios de diseño, patrones de resiliencia y una arquitectura modular, preparada para escenarios reales de producción.
