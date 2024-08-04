# Project API Service for Recruitment Platform

## Overview
This project is an API service for a recruitment platform. It is built using Java and the Spring framework, and it uses Maven for dependency management and build automation.

## Features
- **User Authentication and Authorization**: Supports login and registration endpoints.
- **JWT Token Generation**: Generates JWT tokens for authenticated users.
- **CORS Configuration**: Allows cross-origin requests.
- **CSRF Protection**: CSRF protection is disabled.
- **OAuth2 Resource Server**: Configured to use JWT for securing endpoints.
- **CRUD Operations**: Provides endpoints for creating, updating, retrieving, and deleting resources like seekers and resumes.
- **Swagger Documentation**: API documentation available via Swagger UI.
- **Prometheus Metrics**: Exposes metrics for monitoring via Prometheus.
- **Docker Support**: Dockerfile available for containerizing the application.

## How to Run the Project Using Maven

### Clone the Repository
```sh
git clone https://github.com/duyviet2101/project-BEC-unigap
cd project-BEC-unigap
```

### Generate RSA Keys
```sh
openssl genrsa -out src/main/resources/private.pem 2048
openssl rsa -in src/main/resources/private.pem -outform PEM -pubout -out src/main/resources/public.pem
```

### Build the Project
```sh
mvn clean install
```

### Run the Project
```sh
mvn spring-boot:run
```

### Access the Application
- **Swagger Documentation**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **Prometheus Metrics**: [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)
- **Prometheus Console**: [http://localhost:9090/](http://localhost:9090/)
- **Grafana Console**: [http://localhost:3000/](http://localhost:3000/)

## Docker Instructions

### Build Docker Image
```sh
docker build -t project-BEC-unigap:latest .
```

### Run Docker Container
```sh
docker run --network=host -p 8080:8080 project-BEC-unigap:latest
```

## Links
- **Spring Application Properties**: [Spring Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html)