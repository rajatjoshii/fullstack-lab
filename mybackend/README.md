# my-backend-lab

A simple Spring Boot backend used for learning Java and building REST APIs.
It ships with an in-memory H2 database and OpenAPI/Swagger documentation.

## Prerequisites
- [Java Development Kit (JDK) 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/) 3.x (the provided `mvnw` wrapper can be used instead)

## Running the application
Use the Maven wrapper to start the application:

```bash
./mvnw spring-boot:run
```

The server starts on port **8080** by default. You can also build a runnable jar:

```bash
./mvnw clean package
java -jar target/mybackend-0.0.1-SNAPSHOT.jar
```

## Running tests
Execute the project's tests with:

```bash
./mvnw test
```

## API documentation and consoles
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- H2 database console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (user `sa`, blank password)

## Environment configuration
Configuration defaults are defined in `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
```

To customize settings such as the server port or database connection, edit that file or supply properties at runtime:

```bash
# change the server port
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=9090

# use a file-based H2 database
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.url=jdbc:h2:file:~/mydb"
```

For additional options, consult the [Spring Boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/).


# Docker 

## Build a docker file

```bash
docker build -t my-backend-app .
```

## Run the docker container

```bash
docker run -p 8080:8080 my-backend-app
```

- Starts a container from your image my-backend-app
- Your local port 8080 → container’s port 8080
- http://localhost:8080/swagger-ui/index.html -> swagger
- p 8080:8080 -> Maps a host port (left side) to a container port (right side)

- To check container id and ports run in a separate terminal
```bash
docker ps 
```

# Docker-compose
## Run the docker using the configurations defined in the yml file

```bash
docker-compose up
```
