package com.rit.ecopulse.model;

public class ScalingRecommendationRequest {
    private int currentReplicas = 3;
    private double targetWorkloadRps = 1500.0;
    private double currentCpuUtilization = 0.88; // 88%
    private String cloudRegion = "ap-south-1";

    public ScalingRecommendationRequest() {}

    public ScalingRecommendationRequest(int currentReplicas, double targetWorkloadRps, 
                                       double currentCpuUtilization, String cloudRegion) {
        this.currentReplicas = currentReplicas;
        this.targetWorkloadRps = targetWorkloadRps;
        this.currentCpuUtilization = currentCpuUtilization;
        this.cloudRegion = cloudRegion;
    }

    public int getCurrentReplicas() { return currentReplicas; }
    public void setCurrentReplicas(int currentReplicas) { this.currentReplicas = currentReplicas; }

    public double getTargetWorkloadRps() { return targetWorkloadRps; }
    public void setTargetWorkloadRps(double targetWorkloadRps) { this.targetWorkloadRps = targetWorkloadRps; }

    public double getCurrentCpuUtilization() { return currentCpuUtilization; }
    public void setCurrentCpuUtilization(double currentCpuUtilization) { this.currentCpuUtilization = currentCpuUtilization; }

    public String getCloudRegion() { return cloudRegion; }
    public void setCloudRegion(String cloudRegion) { this.cloudRegion = cloudRegion; }
}
