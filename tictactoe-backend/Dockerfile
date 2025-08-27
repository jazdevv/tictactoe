# Use Java 22 runtime
FROM eclipse-temurin:22-jre-jammy

# Set working directory
WORKDIR /app

# Copy the built JAR into the container
COPY build/libs/*.jar app.jar

# Expose the Spring Boot default port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
