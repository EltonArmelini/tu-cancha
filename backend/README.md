# Documentación del Proyecto
## Reserva tu Cancha - Backend APP

## Índice
1. [Introducción](#introducción)
2. [Librerías Usadas](#librerías-usadas)
3. [Pautas para la estandarización](#pautas-para-la-estandarización)
4. [Arquitectura de la Aplicación](#arquitectura-de-la-aplicación)
5. [Tecnologías Usadas](#tecnologías-usadas)

## Introducción

Este documento proporciona una visión general del proyecto, incluyendo las librerías utilizadas, la forma de describir funciones, la arquitectura de la aplicación y las tecnologías empleadas.
___

## Tecnologías Usadas

    - *Java JDK OpenJDK 17*: La aplicación está desarrollada en Java utilizando OpenJDK 17, una versión estable y de largo soporte del JDK que proporciona características avanzadas y mejoras en el rendimiento.

    - *Docker*: Utilizado para empaquetar y orquestar los servicios de la aplicación en contenedores. Docker facilita la creación de entornos de desarrollo y producción consistentes, y la gestión de dependencias y configuraciones.

    - *MySQL*: Sistema de gestión de bases de datos relacional utilizado para almacenar los datos de la aplicación. MySQL proporciona una base sólida y confiable para el almacenamiento y recuperación de datos.

    - *Maven*: Herramienta de gestión de proyectos y control del ciclo de vida de la aplicación. Maven se utiliza para manejar las dependencias del proyecto, compilar el código, ejecutar pruebas y empaquetar la aplicación en artefactos desplegables.
___

## Librerías Usadas

A continuación se listan las principales librerías utilizadas en el proyecto:

    - **Spring Boot**: Framework para el desarrollo de aplicaciones Java basadas en Spring.
    - **ModelMapper**: Biblioteca para la conversión de objetos entre tipos diferentes, especialmente útil para mapear DTOs y entidades.
    - **Jackson**: Biblioteca para la serialización y deserialización de JSON.
    - **JPA (Java Persistence API)**: Para la persistencia de datos en bases de datos relacionales.
    - **Hibernate**: Implementación de JPA para el manejo de ORM (Object-Relational Mapping).
    - **Lombok**: Biblioteca para reducir el boilerplate code en Java (p. ej., getters y setters).
    - **JUnit**: Framework para pruebas unitarias en Java.
    - **Mockito**: Biblioteca para la creación de mocks y pruebas unitarias.
___

## Arquitectura de la Aplicación

La aplicación sigue una arquitectura en capas, que incluye:

1. **Capa de Presentación**: Maneja las solicitudes HTTP y responde a los clientes. Utiliza controladores (@RestController) para interactuar con el usuario.

2. **Capa de Servicio**: Conervicios (@Service) procesan la lógica y manipulan datos entre la capa de presentación y la capa de persistencia.

3. **Capa de Persistencia**: Maneja la interacción con la base de datos. Utiliza repositorios (@Repository) para realizar operaciones CRUD y otras consultas.

4. **Capa de Modelo**: Define las entidades y DTOs que representan los datos de la aplicación. Las entidades están mapeadas a tablas de base de datos y los DTOs se usan para transferir datos entre capas.

5. **Transferencia de Datos (DTOs)**: Los DTOs se utilizan para transferir datos entre las diferentes capas de la aplicación de manera estructurada y eficiente. Los DTOs ayudan a encapsular la información que se envía y recibe, asegurando que solo los datos necesarios sean incluidos en la comunicación entre el cliente y el servidor, y entre la capa de servicio y la capa de persistencia.

6. **Configuración de la Aplicación**: La configuración de la aplicación se maneja a través del paquete config. Este paquete centraliza la configuración de componentes esenciales, como la configuración de seguridad, la definición de beans y la integración con servicios externos. Permite personalizar el comportamiento de la aplicación y ajustar parámetros globales de manera organizada.

La comunicación entre capas se realiza a través de interfaces y servicios, promoviendo una arquitectura desacoplada y mantenible.
___

## Pautas para la estandarización

En nuestro proyecto, seguimos un conjunto de normas para mantener la claridad y la consistencia en el código. A continuación, se detallan las directrices para la documentación, nomenclatura y generación de la documentación JavaDoc.

### Documentación de Código 
Las funciones, clases, paquetes y otros elementos de código deben estar escritos en inglés. 

### Comentarios y Documentación: 

Los comentarios JavaDoc se deben usar para documentar funciones y clases. Aunque el código está en inglés, los comentarios pueden estar en español para mejorar la comprensión dentro del equipo. Esto incluye descripciones de métodos, parámetros y resultados.

### Ejemplo de Documentación con JavaDoc:

En caso que se quiera ver la documentacion generada revisar el [directorio](docs/)

```bash
mvn javadoc:javadoc
```


Aquí se muestra un ejemplo de cómo documentar una clase y un método usando JavaDoc:

```java
/**
 * Calcula la suma de dos números enteros.
 *
 * @param a El primer número.
 * @param b El segundo número.
 * @return La suma de los dos números.
 */
public int sum(int a, int b) {
    return a + b;
}
```
### Nomenclatura 

Las clases deben tener nombres que describan su función y deben seguir la convención de nomenclatura en inglés.Los nombres de las clases deben terminar con el sufijo correspondiente:
    - Controladores: **Controller** (por ejemplo, UserController)
    - Servicios: **Service** (por ejemplo, UserService)
    - Interfaces: Deben comenzar con **I** (por ejemplo, IUserService)
    - Implementaciones: Deben incluir **Implementation** al final (por ejemplo, UserServiceImplementation)


