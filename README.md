# ESB Demo

## Objetivo
Crear una simulación orientada a un entorno empresarial real, con servicios legacy para implementar o simular un ESB (_Enterprise Service Bus_).

Este proyecto usa Docker Compose para orquestar varios contenedores que representan:
- Un broker RabbitMQ.
- Un servicio ESB basado en Apache Camel y Spring Boot.
- Un servicio SOAP legacy en Java Spring Boot.
- Un servicio de facturación legacy en Python.

## Estructura del proyecto

- `docker-compose.yml`: define los servicios Docker que se levantan juntos.
- `esb/`: código fuente del servicio ESB.
- `legacy-soap/`: código fuente del servicio SOAP legacy.
- `legacy-billing/`: carpeta para el servicio de facturación legacy en Python.
- `gateway/`: carpeta reservada para integraciones o gateway futuros.
- `request.xml`: ejemplo de petición SOAP para el servicio legacy.

## Servicios disponibles

### rabbitmq
- Imagen: `rabbitmq:3-management`
- Puerto de cliente: `5672`
- Puerto de administración web: `15672`
- URL de acceso: `http://localhost:15672`
- Usuario/contraseña estándar: `guest` / `guest`

### esb
- Construido desde `./esb` con Maven y Java 17.
- Expone el puerto `8080` en el host.
- Contiene un proyecto Spring Boot con Apache Camel para enrutar mensajes e integrar servicios.

### legacy-soap
- Construido desde `./legacy-soap` con Maven y Java 17.
- Expone el puerto `8081` en el host, internamente usa `8080`.
- Implementa un endpoint SOAP para `procesarPagoRequest`.
- Usa `pagos.xsd` como esquema XSD para definir el contrato SOAP.

### legacy-billing
- Imagen base: `python:3.11`
- Monta el código local desde `./legacy-billing`.
- Diseñado como un servicio legacy adicional para facturación.

## Requisitos previos

- Docker Desktop instalado y funcionando.
- Conexión a internet para descargar imágenes Docker y dependencias Maven.
- En Windows, es recomendable usar WSL 2 si Docker Desktop lo requiere.

## Uso básico

Desde la carpeta raíz del proyecto:

```bash
docker compose up -d
```

Esto descargará las imágenes necesarias, construirá los servicios `esb` y `legacy-soap`, y levantará los contenedores.

Para detener y eliminar los contenedores:

```bash
docker compose down
```

Para reconstruir las imágenes desde cero:

```bash
docker compose build --no-cache
```

## Ver logs

Para ver los logs de todos los servicios:

```bash
docker compose logs -f
```

Para ver logs de un servicio específico:

```bash
docker compose logs -f legacy-soap
```

## Ejemplo de petición SOAP

El archivo `request.xml` contiene un ejemplo de petición SOAP válida para el servicio `legacy-soap`.

## Notas importantes

- `legacy-soap` requiere que el archivo `pagos.xsd` esté en `src/main/resources/` para que Spring Boot lo cargue correctamente.
- Si tienes problemas al construir con Maven dentro del contenedor, revisa la conectividad a internet y la configuración de proxy en Docker Desktop.
- `gateway/` y `legacy-billing/` actualmente son carpetas de soporte, puedes usarlas para extender esta simulación con más servicios.