# TU Cancha

TU Cancha es una aplicación para la reserva de canchas deportivas. Está diseñada con un frontend moderno en React y un backend robusto en Spring Boot. Los usuarios pueden autenticarse y autorizarse mediante JWT para gestionar sus reservas. La aplicación también incluye notificaciones de correo electrónico a través de Mailtrap y está desplegada en AWS EC2 usando Docker y Ansible. El pipeline CI/CD está automatizado usando GitLab CI/CD.

## Tecnologías Usadas

### Frontend

[![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)](https://reactjs.org/)
[![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS)

### Backend

[![Spring Boot](https://img.shields.io/badge/springboot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/springsecurity-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-security)
[![JPA](https://img.shields.io/badge/jpa-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-data-jpa)
[![Mailtrap](https://img.shields.io/badge/mailtrap-%2323CCEF.svg?style=for-the-badge&logo=mailtrap&logoColor=white)](https://mailtrap.io/)
[![AWS SDK](https://img.shields.io/badge/aws_sdk-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)](https://aws.amazon.com/sdk-for-java/)

### Infraestructura

[![Ansible](https://img.shields.io/badge/ansible-%231A1918.svg?style=for-the-badge&logo=ansible&logoColor=white)](https://www.ansible.com/)
[![AWS EC2](https://img.shields.io/badge/aws_ec2-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)](https://aws.amazon.com/ec2/)
[![Bash](https://img.shields.io/badge/bash-%23121011.svg?style=for-the-badge&logo=gnu-bash&logoColor=white)](https://www.gnu.org/software/bash/)
[![Docker](https://img.shields.io/badge/docker-%232496ED.svg?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![GitLab CI](https://img.shields.io/badge/GitLab%20CI-%23181717.svg?style=for-the-badge&logo=gitlab&logoColor=white)](https://about.gitlab.com/features/ci-cd/)

## Características del Proyecto

- **Frontend:** Interfaz intuitiva para gestionar reservas de canchas deportivas.
- **Backend:** API REST para la gestión de usuarios, reservas, y autorización/autenticación mediante JWT.
- **Autenticación:** Sistema de login/registro seguro con Spring Security y JWT.
- **Notificaciones:** Envío de correos mediante Mailtrap para notificaciones de reserva.
- **Despliegue:** Desplegado en AWS EC2 usando contenedores Docker gestionados con Ansible.
- **CI/CD:** Pipeline de GitLab CI/CD para integración continua y despliegue automatizado.

## Instrucciones para Ejecutar la Aplicación en Local

### 1. Clonar el repositorio

```bash
git clone https://github.com/EltonArmelini/tu-cancha.git
cd tu-cancha
```
### 2. Moverse a front para modificar variables de entonrno
```bash
cd frontend
```
### 3. Copiar el .env.template a .env y colocar el hosturl y el api key de googleMaps
```bash
cd .env.template .env
```
### 4. Moverse a backend y modificar credenciales de aws para poder subir imagenes a s3 y mail para poder enviar mails de notificaciones
```bash
cd ../backend/src/main/resources
```
### 5. Volver a la raiz del proyecto y levantar los contenedores
```bash
cd ../../../../
docker compose up --build -d
```

