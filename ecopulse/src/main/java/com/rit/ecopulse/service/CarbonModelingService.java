package com.rit.ecopulse.service;

import com.rit.ecopulse.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CarbonModelingService {

    // Regional grid carbon intensities in grams CO2e per kWh
    private static final Map<String, Double> GRID_CARBON_INTENSITIES;
    // Data center Power Usage Effectiveness
    private static final Map<String, Double> PUE_RATINGS;

    static {
        Map<String, Double> grid = new HashMap<>();
        grid.put("eu-north-1", 28.0);      // Sweden (Hydro/Clean)
        grid.put("us-west-2", 150.0);      // Oregon (Hydro)
        grid.put("eu-west-1", 280.0);      // Ireland
        grid.put("us-east-1", 379.0);      // Virginia
        grid.put("ap-south-1", 660.0);     // Mumbai, India (Coal-heavy)
        GRID_CARBON_INTENSITIES = Collections.unmodifiableMap(grid);

        Map<String, Double> pue = new HashMap<>();
        pue.put("GCP", 1.10);
        pue.put("AWS", 1.15);
        pue.put("AZURE", 1.18);
        pue.put("ON_PREM", 1.50);
        PUE_RATINGS = Collections.unmodifiableMap(pue);
    }

    @Value("${ecopulse.default.region:ap-south-1}")
    private String defaultRegion = "ap-south-1";

    public String getDefaultRegion() {
        return defaultRegion;
    }

    public void setDefaultRegion(String defaultRegion) {
        this.defaultRegion = defaultRegion;
    }

    public double getGridIntensity(String region) {
        if (region == null) return GRID_CARBON_INTENSITIES.get(defaultRegion);
        return GRID_CARBON_INTENSITIES.getOrDefault(region.toLowerCase().trim(), 500.0);
    }

    public double getPue(String provider) {
        if (provider == null) return 1.15;
        return PUE_RATINGS.getOrDefault(provider.toUpperCase().trim(), 1.15);
    }

    public CarbonEstimateResponse estimateWorkload(CarbonEstimateRequest req) {
        long start = System.currentTimeMillis();

        if (req == null) {
            return new CarbonEstimateResponse("INVALID", 0.0, 0.0, 500.0, 1.15, "C_CARBON_INTENSIVE", 0);
        }

        String region = (req.getCloudRegion() != null && !req.getCloudRegion().isBlank()) 
                ? req.getCloudRegion() : defaultRegion;
        double gridIntensity = getGridIntensity(region);
        double pue = getPue(req.getCloudProvider());

        // Thermal Design Power modeling
        double util = Math.min(1.0, Math.max(0.05, req.getAverageCpuUtilization()));
        double vCpuWatts = req.getvCpuCount() * 15.0 * (0.25 + (0.75 * util));
        double ramWatts = Math.max(0.5, req.getMemoryGb()) * 0.38;

        double totalServerWatts = vCpuWatts + ramWatts;
        double totalPowerKw = totalServerWatts / 1000.0;
        double energyKwh = totalPowerKw * Math.max(0.1, req.getRuntimeHours()) * pue;
        double carbonEmissionsKg = (energyKwh * gridIntensity) / 1000.0;

        String tier;
        if (gridIntensity <= 100.0) {
            tier = "A_ECO_OPTIMAL";
        } else if (gridIntensity <= 350.0) {
            tier = "B_MODERATE";
        } else {
            tier = "C_CARBON_INTENSIVE";
        }

        long latency = Math.max(1, System.currentTimeMillis() - start);
        return new CarbonEstimateResponse(req.getWorkloadName(), energyKwh, carbonEmissionsKg, 
                gridIntensity, pue, tier, latency);
    }

    public ScalingRecommendationResponse optimizeScaling(ScalingRecommendationRequest req) {
        int currentReplicas = Math.max(1, req.getCurrentReplicas());
        String region = (req.getCloudRegion() != null) ? req.getCloudRegion().toLowerCase().trim() : defaultRegion;
        double gridIntensity = getGridIntensity(region);

        // Autoscaling decision logic
        int recommendedReplicas;
        if (req.getCurrentCpuUtilization() > 0.75 || req.getTargetWorkloadRps() > (currentReplicas * 400.0)) {
            // Recommends scaling out by +2 pods (e.g. 3 -> 5 replicas)
            recommendedReplicas = Math.max(currentReplicas + 2, 5);
        } else if (req.getCurrentCpuUtilization() < 0.30 && currentReplicas > 3) {
            recommendedReplicas = Math.max(3, currentReplicas - 2);
        } else {
            recommendedReplicas = currentReplicas;
        }

        // Compute power per replica per hour (approx 0.055 kW per pod worker)
        double kwPerPod = 0.055;
        double pue = 1.15;
        double currentCarbonPerHour = (currentReplicas * kwPerPod * pue * gridIntensity) / 1000.0;
        double projectedCarbonPerHour = (recommendedReplicas * kwPerPod * pue * gridIntensity) / 1000.0;

        // Clean region comparison (eu-north-1 Stockholm: 28 gCO2/kWh)
        String cleanRegion = "eu-north-1";
        double cleanGrid = getGridIntensity(cleanRegion);
        double cleanCarbonPerHour = (recommendedReplicas * kwPerPod * pue * cleanGrid) / 1000.0;

        double savingsPct = ((projectedCarbonPerHour - cleanCarbonPerHour) / projectedCarbonPerHour) * 100.0;
        savingsPct = Math.max(0.0, savingsPct);

        String rationale = String.format(
                "Scaling from %d to %d replicas resolves CPU throttling under %.0f RPS workload. " +
                "Hosting in %s emits %.3f kg CO2/hr, while shifting to %s yields a %.1f%% carbon reduction (%.3f kg CO2/hr).",
                currentReplicas, recommendedReplicas, req.getTargetWorkloadRps(), region, 
                projectedCarbonPerHour, cleanRegion, savingsPct, cleanCarbonPerHour
        );

        return new ScalingRecommendationResponse(currentReplicas, recommendedReplicas, 
                currentCarbonPerHour, projectedCarbonPerHour, cleanRegion, cleanCarbonPerHour, 
                savingsPct, rationale);
    }
}
