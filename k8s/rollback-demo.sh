#!/bin/bash
# ==============================================================================
# Component 2 Enhancement: Kubernetes Automated Rollback Demonstration
# Student USN: 1MS24CI060 | Ramaiah Institute of Technology
# Course: 25CIAEC59 (DevOps Self Learning Activity - Component 2)
# ==============================================================================

set -e

echo "=================================================================="
echo "  ECOPULSE KUBERNETES ROLLBACK DEMO (USN: 1MS24CI060)             "
echo "=================================================================="

echo -e "\n--> Phase 1: Checking Baseline Working Deployment"
kubectl rollout history deployment/ecopulse-aiops
kubectl get pods -l app=ecopulse-aiops -o wide
echo -e "\nTesting Health Endpoint on Baseline:"
curl -s http://localhost:30080/health | grep "1MS24CI060" && echo " Baseline is healthy!"

echo -e "\n--> Phase 2: Simulating Failure - Intentionally Deploying Broken Image (v2-broken)"
kubectl set image deployment/ecopulse-aiops ecopulse-aiops=ecopulse-aiops:v2-broken

echo -e "\n--> Phase 3: Observing Kubernetes Catching the Deployment Failure"
sleep 6
kubectl get pods -l app=ecopulse-aiops -o wide
echo -e "\nNotice: Kubernetes isolates the bad pod (ErrImagePull / ImagePullBackOff)."
echo "Existing working pods continue serving live traffic without downtime:"
curl -s http://localhost:30080/health | grep "1MS24CI060" && echo " Zero-downtime traffic protection verified!"

echo -e "\n--> Phase 4: Executing Rollback (kubectl rollout undo)"
kubectl rollout undo deployment/ecopulse-aiops

echo -e "\n--> Phase 5: Waiting for Rollout to Recover"
kubectl rollout status deployment/ecopulse-aiops --timeout=30s

echo -e "\n--> Phase 6: Final Verification - All Pods Restored and Healthy"
kubectl get pods -l app=ecopulse-aiops -o wide
echo -e "\nRollout Revision History:"
kubectl rollout history deployment/ecopulse-aiops
curl -s http://localhost:30080/health | grep "1MS24CI060" && echo " Rollback successful! App fully recovered."

echo -e "\n=================================================================="
echo "  ROLLBACK DEMONSTRATION COMPLETE (1MS24CI060)                    "
echo "=================================================================="
