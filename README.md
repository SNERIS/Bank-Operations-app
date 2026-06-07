# Bank Operations App

Backend REST API per operacione bankare: register, login me JWT, deposit, withdraw, transfer dhe history.

## Tech Stack

- Java 25
- Spring Boot 4.0.6
- Spring Security + JWT
- Spring Data JPA / Hibernate
- MySQL
- Swagger
- Docker

## Database

Databaza:

```text
my1application
```

Konfigurimi te `src/main/resources/application.properties` merr vlerat nga environment variables:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

Krijo nje file `.env` lokal nga `.env.example` dhe vendos kredencialet aty. File `.env` nuk behet push ne GitHub.

```bash
cp .env.example .env
```

## Build

```bash
./mvnw clean package -DskipTests
```

## Run Local

```bash
./mvnw spring-boot:run
```

Ose:

```bash
java -jar target/my1project-0.0.1-SNAPSHOT.jar
```

## Docker

Build image:

```bash
docker build -t bank-backend .
```

Run me Docker Compose:

```bash
docker compose up --build
```

Run vetem backend-in me Docker:

```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/my1application \
  -e SPRING_DATASOURCE_USERNAME=<username> \
  -e SPRING_DATASOURCE_PASSWORD=<password> \
  bank-backend
```

Nese porta 8080 eshte e zene:

```bash
docker ps
docker stop <container-name>
```

## Swagger

```text
http://localhost:8080/swagger-ui/index.html
```

## API Kryesore

Login:

```text
POST /api/auth/login
```

Register:

```text
POST /api/users/register
```

Deposit:

```text
PUT /api/users/deposit
```

Withdraw:

```text
PUT /api/users/withdraw
```

Transfer:

```text
PUT /api/users/transfer
```

History:

```text
GET /api/users/history
```

Per endpoint-et e mbrojtura perdor header:

```http
Authorization: Bearer <TOKEN>
```

## Git

```bash
git add .
git commit -m "Shtuar ndryshime"
git push
```
