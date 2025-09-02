# --- STAGE 1: build ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
# Construye y reempaqueta el fat jar
RUN mvn -q -DskipTests package spring-boot:repackage \
 && JAR="$(ls target/*.jar | grep -v original | head -n1)" \
 && echo "Repackaged JAR: $JAR" \
 && cp "$JAR" target/app.jar

# --- STAGE 2: runtime ---
FROM eclipse-temurin:17-jre-alpine
RUN apk add --no-cache curl
WORKDIR /app

COPY --from=build /app/target/app.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar","--spring.profiles.active=docker"]
