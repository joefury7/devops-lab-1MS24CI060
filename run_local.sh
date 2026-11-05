#!/bin/bash
# ==============================================================================
# EcoPulse AIOps: Local Build & Run Script
# Student USN: 1MS24CI060 | Ramaiah Institute of Technology
# ==============================================================================

set -e

echo "=================================================================="
echo "  ECOPULSE AIOPS: COMPILING & RUNNING LOCALLY (USN: 1MS24CI060)  "
echo "=================================================================="

# 1. Clean & Run Unit Tests
echo -e "\n[1/3] Running JUnit 5 Unit Tests with Apache Maven..."
mvn clean test -f ecopulse/pom.xml

# 2. Package Executable JAR
echo -e "\n[2/3] Packaging Executable Spring Boot JAR..."
mvn package -DskipTests -f ecopulse/pom.xml

# 3. Launch Microservice
echo -e "\n[3/3] Launching EcoPulse AIOps on http://localhost:8080..."
echo "------------------------------------------------------------------"
echo "  * Web Dashboard:       http://localhost:8080/"
echo "  * Health Check Probe:  http://localhost:8080/health"
echo "  * AIOps Incident API:  http://localhost:8080/api/v1/aiops/assess"
echo "  * Carbon Footprint:    http://localhost:8080/api/v1/carbon/estimate"
echo "  * Scaling Optimizer:   http://localhost:8080/api/v1/aiops/optimize-scaling"
echo "  * Pod Green Telemetry: http://localhost:8080/api/v1/telemetry/green"
echo "------------------------------------------------------------------"
echo "Press Ctrl+C to stop the application."

java -jar ecopulse/target/ecopulse-aiops-1.0.0.jar
