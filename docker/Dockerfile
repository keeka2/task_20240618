# Step: Build
FROM eclipse-temurin:17 AS builder
WORKDIR /app

COPY gradlew build.gradle.kts settings.gradle.kts ./

COPY gradle ./gradle

COPY src/main ./src/main

RUN ./gradlew bootJar

# Step: Run
FROM eclipse-temurin:17

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", \
            "-jar", \
            "app.jar"]