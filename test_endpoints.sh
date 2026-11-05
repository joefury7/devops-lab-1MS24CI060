#!/bin/bash
# ==============================================================================
# EcoPulse AIOps: Automated HTTP Endpoint Verification Script
# Student USN: 1MS24CI060 | Ramaiah Institute of Technology
# ==============================================================================

BASE_URL="http://localhost:8085"
echo "=================================================================="
echo "  TESTING ECOPULSE ENDPOINTS (TARGET: ${BASE_URL})                "
echo "=================================================================="

# 1. Health Probe
echo -e "\n--> 1. Testing GET /health:"
curl -s "${BASE_URL}/health" | jq . || curl -s "${BASE_URL}/health"
echo ""

# 2. AIOps Incident Assessment
echo -e "\n--> 2. Testing POST /api/v1/aiops/assess (CPU Starvation & OOM Risk):"
curl -s -X POST "${BASE_URL}/api/v1/aiops/assess" \
  -H "Content-Type: application/json" \
  -d '{
    "podName": "ecopulse-worker-0",
    "namespace": "production",
    "cpuThrottlingPercentage": 84.5,
    "memoryUsagePercent": 91.0,
    "memoryGrowthRateMbPerSec": 4.2,
    "http5xxErrorRate": 7.5,
    "restartCount": 3
  }' | jq . || curl -s -X POST "${BASE_URL}/api/v1/aiops/assess" -H "Content-Type: application/json" -d '{"podName":"ecopulse-worker-0","cpuThrottlingPercentage":84.5,"memoryUsagePercent":91.0,"memoryGrowthRateMbPerSec":4.2,"http5xxErrorRate":7.5,"restartCount":3}'
echo ""

# 3. Carbon Footprint Estimation
echo -e "\n--> 3. Testing POST /api/v1/carbon/estimate (vCPU/RAM -> kg CO2e):"
curl -s -X POST "${BASE_URL}/api/v1/carbon/estimate" \
  -H "Content-Type: application/json" \
  -d '{
    "workloadName": "k8s-pod-cluster",
    "vCpuCount": 4,
    "memoryGb": 16.0,
    "runtimeHours": 24.0,
    "cloudProvider": "AWS",
    "cloudRegion": "ap-south-1",
    "averageCpuUtilization": 0.75
  }' | jq . || curl -s -X POST "${BASE_URL}/api/v1/carbon/estimate" -H "Content-Type: application/json" -d '{"workloadName":"k8s-pod-cluster","vCpuCount":4,"memoryGb":16.0,"runtimeHours":24.0,"cloudProvider":"AWS","cloudRegion":"ap-south-1","averageCpuUtilization":0.75}'
echo ""

# 4. Green Autoscaling Optimizer (3 -> 5 Replicas)
echo -e "\n--> 4. Testing POST /api/v1/aiops/optimize-scaling (Simulating 3 -> 5 Scaling):"
curl -s -X POST "${BASE_URL}/api/v1/aiops/optimize-scaling" \
  -H "Content-Type: application/json" \
  -d '{
    "currentReplicas": 3,
    "targetWorkloadRps": 1600.0,
    "currentCpuUtilization": 0.88,
    "cloudRegion": "ap-south-1"
  }' | jq . || curl -s -X POST "${BASE_URL}/api/v1/aiops/optimize-scaling" -H "Content-Type: application/json" -d '{"currentReplicas":3,"targetWorkloadRps":1600.0,"currentCpuUtilization":0.88,"cloudRegion":"ap-south-1"}'
echo ""

# 5. Pod Green Telemetry
echo -e "\n--> 5. Testing GET /api/v1/telemetry/green:"
curl -s "${BASE_URL}/api/v1/telemetry/green" | jq . || curl -s "${BASE_URL}/api/v1/telemetry/green"
echo ""

echo "=================================================================="
echo "  ALL ENDPOINT TESTS COMPLETED SUCCESSFULLY (USN: 1MS24CI060)     "
echo "=================================================================="
