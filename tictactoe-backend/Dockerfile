# Stage 1: Build the app
FROM gradle:8.3-jdk17 AS build

WORKDIR /app

# Copy only necessary files first for caching dependencies
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Fetch dependencies (cached if unchanged)
RUN gradle build --no-daemon -x test || true

# Copy the rest of the source code
COPY src ./src

# Build the JAR
RUN gradle clean build --no-daemon -x test

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
