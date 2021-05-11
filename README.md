# Customer Support Service

## Technology Stack:

* Kotlin
* Gradle
* Spring Framework
* Spring Boot
* Spring MVC
* Spring Data Jpa
* Spring Security (JWT)
* Postgres
* Hibernate
* Flyway
* OpenApi (Swagger)

## How to Run:

* First under `docker/postgres` directory run:
  `docker-compose up`
* After that under root directory run `./gradlew bootRun`

## Admin

At Startup a user with admin role will be created with following credentials:

* username: aliakbar
* password: anotherPass

## Browse API Documentation

After running project successfully go to
the : [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)