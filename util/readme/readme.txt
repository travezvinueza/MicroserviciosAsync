Proyecto realizado bajo mvc con arquitetcura de microservicios

Clonar proyecto desde el repositorio.

Descargar las dependencias del pom xml.

El proyecto fue desarrollado con flyway para la creacion de tablas y carga de datos iniciales, dentro del archivo: /resources/.. se encuentran el script para creacion de tablas y carga de datos iniciales.

El primer servicio se deplega en el puerto 8080 y el segundo servicio en e 8081 de acuerdo a la configuración en el archivo application.properties

El contextPath de la app esta configurado con el valor /api.

Para probar los endpoints direccionar a la carpeta util en la raiz ejeuctar con postman

El proyecto tiene la swagger para la documentacion y test de end points y pruebas de integracion, para ingresar apuntar la siguiente ruta: http://localhost:8080/api/swagger-ui/index.html#/

El diagrama de la solución se encuentra en la ruta raiz