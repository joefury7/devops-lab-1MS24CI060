# ==============================================================================
# Multi-Stage Dockerfile for EcoPulse AIOps (Root Build Context)
# Student USN: 1MS24CI060 | Ramaiah Institute of Technology
# ==============================================================================

FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /workspace

COPY pom.xml .
COPY ecopulse/pom.xml ecopulse/
COPY ecopulse/src ecopulse/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
LABEL maintainer="1MS24CI060@msrit.edu" \
      service="ecopulse-aiops" \
      version="1.0.0"

WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
COPY --from=builder /workspace/ecopulse/target/ecopulse-aiops-1.0.0.jar /app/ecopulse-aiops.jar
RUN chown -R appuser:appgroup /app
USER appuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/ecopulse-aiops.jar"]
