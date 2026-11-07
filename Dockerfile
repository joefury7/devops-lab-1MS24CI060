# ==============================================================================
# Production Optimized Dockerfile for EcoPulse AIOps
# Student USN: 1MS24CI060 | M. S. Ramaiah Institute of Technology
# ==============================================================================
FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="1MS24CI060@msrit.edu" \
      service="ecopulse-aiops" \
      version="1.0.0" \
      course="25CIAEC59"

WORKDIR /app

# Security: Non-root user and group
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy pre-built production JAR from ecopulse target
COPY ecopulse/target/ecopulse-aiops-1.0.0.jar /app/ecopulse-aiops.jar

RUN chown -R appuser:appgroup /app

USER appuser

EXPOSE 8085

ENV SERVER_PORT=8085 \
    ECOPULSE_DEFAULT_REGION=ap-south-1

ENTRYPOINT ["java", "-Xms128m", "-Xmx512m", "-jar", "/app/ecopulse-aiops.jar"]
