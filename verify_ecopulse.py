import json
import math

print("=== VERIFYING ECOPULSE AIOPS BACKEND LOGIC & DATA CONTRACTS ===")

# 1. Test Incident Prediction Engine
def assess_incident(pod_name, cpu_throttling, memory_percent, growth_rate, err_5xx, restarts):
    score = 0.05
    factors = []
    
    if cpu_throttling > 75.0:
        score += 0.35
        factors.append(f"CRITICAL_CPU_THROTTLING_DETECTED (>{cpu_throttling}%)")
    elif cpu_throttling > 40.0:
        score += 0.15
        factors.append("MODERATE_CPU_THROTTLING_ELEVATION")
        
    if memory_percent > 85.0:
        score += 0.40
        factors.append(f"NEAR_CAPACITY_MEMORY_EXHAUSTION (>{memory_percent}%)")
    elif memory_percent > 70.0:
        score += 0.15
        factors.append("ELEVATED_MEMORY_PRESSURE")
        
    if growth_rate > 3.0:
        score += 0.30
        factors.append(f"RAPID_MEMORY_LEAK_TRAJECTORY ({growth_rate} MB/s)")
        
    if err_5xx > 5.0:
        score += 0.25
        factors.append(f"ELEVATED_5XX_ERROR_VELOCITY ({err_5xx}%)")
        
    if restarts >= 3:
        score += 0.20
        factors.append(f"HIGH_CONTAINER_RESTART_COUNT ({restarts} restarts)")
        
    score = min(1.0, max(0.0, score))
    
    if score >= 0.70:
        alert = "OOM_KILL_IMMOBILE"
        action = "Scale up replica set immediately (3 -> 5) and increase container memory limit to prevent CrashLoopBackOff."
    elif score >= 0.40:
        alert = "CPU_STARVATION_WARNING"
        action = "Increase deployment CPU request threshold or schedule horizontal pod autoscaling."
    else:
        alert = "HEALTHY"
        action = "Pod operating normally within configured SLO thresholds."
        
    return {
        "podName": pod_name,
        "failureRiskScore": round(score, 2),
        "incidentAlert": alert,
        "riskFactors": factors,
        "recommendedAction": action,
        "studentUsn": "1MS24CI060"
    }

# Run Incident Test
res1 = assess_incident("ecopulse-worker-0", 84.5, 91.0, 4.2, 7.5, 3)
assert res1["failureRiskScore"] >= 0.70, "Should detect high risk"
assert res1["incidentAlert"] == "OOM_KILL_IMMOBILE", "Alert should be OOM_KILL_IMMOBILE"
print(" [PASS] Incident Anomaly Detector verified: Risk Score =", res1["failureRiskScore"], "| Alert =", res1["incidentAlert"])

# 2. Test Carbon Modeling Engine
GRID_CARBON = {
    "eu-north-1": 28.0,
    "us-west-2": 150.0,
    "eu-west-1": 280.0,
    "us-east-1": 379.0,
    "ap-south-1": 660.0
}
PUE = {"AWS": 1.15, "GCP": 1.10, "AZURE": 1.18, "ON_PREM": 1.50}

def estimate_carbon(workload_name, vcpu, mem_gb, hours, provider, region, avg_util):
    grid = GRID_CARBON.get(region, 500.0)
    pue_val = PUE.get(provider, 1.15)
    
    util = min(1.0, max(0.05, avg_util))
    vcpu_watts = vcpu * 15.0 * (0.25 + (0.75 * util))
    ram_watts = max(0.5, mem_gb) * 0.38
    total_kw = (vcpu_watts + ram_watts) / 1000.0
    energy_kwh = total_kw * hours * pue_val
    carbon_kg = (energy_kwh * grid) / 1000.0
    
    tier = "A_ECO_OPTIMAL" if grid <= 100 else ("B_MODERATE" if grid <= 350 else "C_CARBON_INTENSIVE")
    
    return {
        "workloadName": workload_name,
        "energyConsumptionKwh": round(energy_kwh, 3),
        "carbonEmissionsKgCo2": round(carbon_kg, 3),
        "gridCarbonIntensity": grid,
        "pue": pue_val,
        "efficiencyTier": tier,
        "studentUsn": "1MS24CI060"
    }

# Run Carbon Test
res2 = estimate_carbon("k8s-pod-cluster", 4, 16.0, 24.0, "AWS", "ap-south-1", 0.75)
assert res2["energyConsumptionKwh"] > 0, "Energy should be positive"
assert res2["carbonEmissionsKgCo2"] > 0, "Carbon should be positive"
assert res2["efficiencyTier"] == "C_CARBON_INTENSIVE", "Mumbai grid should be C tier"
print(" [PASS] Carbon Modeling Service verified: Energy =", res2["energyConsumptionKwh"], "kWh | Carbon =", res2["carbonEmissionsKgCo2"], "kg CO2e")

# 3. Test Scaling & Green Autoscaling Optimizer
def optimize_scaling(current_replicas, target_rps, cpu_util, region="ap-south-1"):
    grid = GRID_CARBON.get(region, 660.0)
    clean_grid = GRID_CARBON["eu-north-1"]
    
    if cpu_util > 0.75 or target_rps > (current_replicas * 400.0):
        recommended = max(current_replicas + 2, 5)
    elif cpu_util < 0.30 and current_replicas > 3:
        recommended = max(3, current_replicas - 2)
    else:
        recommended = current_replicas
        
    kw_per_pod = 0.055
    pue_val = 1.15
    curr_carbon = (current_replicas * kw_per_pod * pue_val * grid) / 1000.0
    proj_carbon = (recommended * kw_per_pod * pue_val * grid) / 1000.0
    clean_carbon = (recommended * kw_per_pod * pue_val * clean_grid) / 1000.0
    
    savings_pct = max(0.0, ((proj_carbon - clean_carbon) / proj_carbon) * 100.0)
    
    return {
        "currentReplicas": current_replicas,
        "recommendedReplicas": recommended,
        "currentCarbonKgPerHour": round(curr_carbon, 3),
        "projectedCarbonKgPerHour": round(proj_carbon, 3),
        "cleanRegionCarbonKgPerHour": round(clean_carbon, 3),
        "carbonSavingsPercent": round(savings_pct, 1),
        "studentUsn": "1MS24CI060"
    }

res3 = optimize_scaling(3, 1600.0, 0.88, "ap-south-1")
assert res3["recommendedReplicas"] == 5, "Autoscaler must scale from 3 to 5 replicas"
assert res3["carbonSavingsPercent"] > 90.0, "Clean Sweden grid should save > 90% carbon"
print(" [PASS] Green Autoscaling Optimizer verified: Replicas 3 ->", res3["recommendedReplicas"], "| Clean Savings =", res3["carbonSavingsPercent"], "%")

print("\n=== ALL ECOPULSE AIOPS BACKEND LOGIC VERIFIED WITH ZERO ERRORS ===")
