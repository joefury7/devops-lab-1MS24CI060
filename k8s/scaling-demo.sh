#!/bin/bash
# ==============================================================================
# Kubernetes Deployment & Autoscaling Demonstration Script
# Student USN: 1MS24CI060 | M. S. Ramaiah Institute of Technology
# Course: 25CIAEC59 (DevOps Self Learning Activity - Component 2)
# ==============================================================================

set -e

echo "=== 1. Deploying EcoPulse AIOps ConfigMap, Deployment & Service ==="
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

echo -e "\n=== 2. Waiting for Initial 3 Pod Replicas to Become Ready ==="
kubectl rollout status deployment/ecopulse-aiops --timeout=60s

echo -e "\n=== 3. Inspecting Initial 3 Running Pods ==="
kubectl get pods -l app=ecopulse-aiops -o wide
kubectl get svc ecopulse-service

echo -e "\n=== 4. Testing Application Health via NodePort (Port 30080) ==="
curl -s http://localhost:30080/health | grep "1MS24CI060" && echo " Health check passed!" || echo "Health check returned non-200"

echo -e "\n=== 5. DEMONSTRATING SCALING: Scaling Replicas from 3 to 5 ==="
kubectl scale deployment ecopulse-aiops --replicas=5

echo -e "\n=== 6. Observing Kubernetes ReplicaSet Reconciliation ==="
kubectl rollout status deployment/ecopulse-aiops --timeout=60s
kubectl get pods -l app=ecopulse-aiops -o wide

echo -e "\n=== 7. Scaling Back to Baseline (5 -> 3 Replicas) ==="
kubectl scale deployment ecopulse-aiops --replicas=3
kubectl rollout status deployment/ecopulse-aiops --timeout=60s
kubectl get pods -l app=ecopulse-aiops -o wide

echo -e "\n=================================================================="
echo "  ECOPULSE KUBERNETES SCALING DEMONSTRATION COMPLETE (1MS24CI060) "
echo "=================================================================="
