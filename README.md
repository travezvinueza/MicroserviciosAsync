# Programación Reactiva con RxJava

Proyecto realizado bajo mvc con arquitetcura de microservicios para clientes y sus movimientos! Este proyecto está desarrollado en Java utilizando Spring

## Tecnologías Utilizadas

- Spring Boot: Para la construcción de microservicios.
- RxJava: Para la comunicacion asincrona.
- Flyway: Para la creacion de tablas y carga de datos iniciales.
- PostgreSQL: Bases de datos relacionales para el almacenamiento de datos.
- Docker: Para la contenerización y despliegue en cualquier entorno.
- ModelMapper: Para mapear objetos de un modelo a otro.


# Configuración

Dentro del archivo: /resources/.. se encuentran el script para creacion de tablas y carga de datos iniciales. El contextPath de la app esta configurado con el valor /api. De acuerdo a la configuración en el archivo application.properties.
- El primer servicio se deplega en el puerto 8080 
- El segundo servicio se deplega en el puerto 8081

El proyecto tiene swagger para la documentacion y test de endpoints y pruebas de integracion. Para probar los endpoints direccionarse a la carpeta util en la raiz y ejeuctar con postman

#### Para ingresar apuntar a la siguiente ruta

```
  http://localhost:8080/api/swagger-ui/index.html#/
```

 

## Diagrama de la arquitectura

![App Screenshot](https://res.cloudinary.com/duzogl1l3/image/upload/v1725459251/microservicios_sxghpl.png)
