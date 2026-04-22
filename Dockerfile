# Usamos una imagen de Maven para compilar el proyecto
FROM maven:3.8.5-openjdk-17 AS build
COPY src .
RUN mvn clean package -DskipTests

# Usamos una imagen ligera de Java para ejecutar la app
FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]