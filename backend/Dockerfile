# --- STAGE 1: build ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cache de dependencias
COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

# Código fuente
COPY src ./src
RUN mvn -q -DskipTests package

# --- STAGE 2: runtime ---
FROM eclipse-temurin:17-jre-alpine
# para el healthcheck del compose
RUN apk add --no-cache curl
WORKDIR /app

# OJO: el alias 'build' debe existir en el stage anterior
# y el patrón del jar debe coincidir con tu nombre de artefacto
COPY --from=build /app/target/*-SNAPSHOT.jar /app/app.jar
# (si prefieres) COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar","--spring.profiles.active=docker"]
