package com.rit.ecopulse.service;

import com.rit.ecopulse.model.IncidentAssessmentRequest;
import com.rit.ecopulse.model.IncidentAssessmentResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncidentPredictionService {

    public IncidentAssessmentResponse assessPodIncident(IncidentAssessmentRequest request) {
        long startTime = System.currentTimeMillis();
        List<String> riskFactors = new ArrayList<>();
        double score = 0.05; // Base operating variance

        if (request == null) {
            return new IncidentAssessmentResponse("UNKNOWN", 1.0, "OOM_KILL_IMMOBILE", 
                    List.of("NULL_METRIC_PAYLOAD"), "Inspect container telemetry immediately.", 0);
        }

        // 1. CPU Throttling Evaluation
        if (request.getCpuThrottlingPercentage() > 75.0) {
            score += 0.35;
            riskFactors.add("CRITICAL_CPU_THROTTLING_DETECTED (>" + request.getCpuThrottlingPercentage() + "%)");
        } else if (request.getCpuThrottlingPercentage() > 40.0) {
            score += 0.15;
            riskFactors.add("MODERATE_CPU_THROTTLING_ELEVATION");
        }

        // 2. Memory Saturation
        if (request.getMemoryUsagePercent() > 85.0) {
            score += 0.40;
            riskFactors.add("NEAR_CAPACITY_MEMORY_EXHAUSTION (>" + request.getMemoryUsagePercent() + "%)");
        } else if (request.getMemoryUsagePercent() > 70.0) {
            score += 0.15;
            riskFactors.add("ELEVATED_MEMORY_PRESSURE");
        }

        // 3. Memory Growth Rate / Leak Trajectory
        if (request.getMemoryGrowthRateMbPerSec() > 3.0) {
            score += 0.30;
            riskFactors.add("RAPID_MEMORY_LEAK_TRAJECTORY (" + request.getMemoryGrowthRateMbPerSec() + " MB/s)");
        }

        // 4. HTTP 5xx Error Velocity
        if (request.getHttp5xxErrorRate() > 5.0) {
            score += 0.25;
            riskFactors.add("ELEVATED_5XX_ERROR_VELOCITY (" + request.getHttp5xxErrorRate() + "%)");
        }

        // 5. Historic Pod Restarts
        if (request.getRestartCount() >= 3) {
            score += 0.20;
            riskFactors.add("HIGH_CONTAINER_RESTART_COUNT (" + request.getRestartCount() + " restarts)");
        }

        score = Math.min(1.0, Math.max(0.0, score));

        String alert;
        String action;
        if (score >= 0.70) {
            alert = "OOM_KILL_IMMOBILE";
            action = "Scale up replica set immediately (3 -> 5) and increase container memory limit to prevent CrashLoopBackOff.";
        } else if (score >= 0.40) {
            alert = "CPU_STARVATION_WARNING";
            action = "Increase deployment CPU request threshold or schedule horizontal pod autoscaling.";
        } else {
            alert = "HEALTHY";
            action = "Pod operating normally within configured SLO thresholds.";
        }

        long latency = Math.max(1, System.currentTimeMillis() - startTime);
        return new IncidentAssessmentResponse(request.getPodName(), score, alert, riskFactors, action, latency);
    }
}
