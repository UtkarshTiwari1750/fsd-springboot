# Use a lightweight OpenJDK base image
FROM openjdk:17-jdk-slim

# Set environment variables
ENV APP_NAME=spring-boot-app
ENV APP_VERSION=1.0.0

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on (usually 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
