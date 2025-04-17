# ─── 1) Build stage ────────────────────────────────────────────────────────────
FROM maven:3.9.0-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy maven files & download deps (cached until pom.xml changes)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy in your source & build
COPY src ./src
RUN mvn clean package 

# ─── 2) Runtime stage ─────────────────────────────────────────────────────────
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the jar from the builder
COPY --from=builder /workspace/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
