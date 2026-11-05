# ==============================================================================
# Multi-Stage Dockerfile for EcoPulse AIOps
# Student USN: 1MS24CI060 | Ramaiah Institute of Technology
# Component 2 Enhancement: Multi-stage build (Builder + Slim Alpine Runtime)
# ==============================================================================

# Stage 1: Builder Stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /workspace

# Copy Maven POM and source code
COPY pom.xml .
COPY src ./src

# Build production artifact
RUN mvn clean package -DskipTests

# Stage 2: Minimal Production Runtime
FROM eclipse-temurin:17-jre-alpine
LABEL maintainer="1MS24CI060@msrit.edu" \
      service="ecopulse-aiops" \
      version="1.0.0"

WORKDIR /app

# Security best practice: Run as unprivileged non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy compiled JAR from builder stage
COPY --from=builder /workspace/target/ecopulse-aiops-1.0.0.jar /app/ecopulse-aiops.jar

RUN chown -R appuser:appgroup /app

USER appuser

EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/ecopulse-aiops.jar"]
