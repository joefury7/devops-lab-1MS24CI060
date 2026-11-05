package com.rit.ecopulse.model;

public class ScalingRecommendationResponse {
    private int currentReplicas;
    private int recommendedReplicas;
    private double currentCarbonKgPerHour;
    private double projectedCarbonKgPerHour;
    private String carbonOptimizedRegion = "eu-north-1";
    private double cleanRegionCarbonKgPerHour;
    private double carbonSavingsPercent;
    private String rationale;
    private String studentUsn = "1MS24CI060";

    public ScalingRecommendationResponse() {}

    public ScalingRecommendationResponse(int currentReplicas, int recommendedReplicas, 
                                         double currentCarbonKgPerHour, double projectedCarbonKgPerHour, 
                                         String carbonOptimizedRegion, double cleanRegionCarbonKgPerHour, 
                                         double carbonSavingsPercent, String rationale) {
        this.currentReplicas = currentReplicas;
        this.recommendedReplicas = recommendedReplicas;
        this.currentCarbonKgPerHour = Math.round(currentCarbonKgPerHour * 1000.0) / 1000.0;
        this.projectedCarbonKgPerHour = Math.round(projectedCarbonKgPerHour * 1000.0) / 1000.0;
        this.carbonOptimizedRegion = carbonOptimizedRegion;
        this.cleanRegionCarbonKgPerHour = Math.round(cleanRegionCarbonKgPerHour * 1000.0) / 1000.0;
        this.carbonSavingsPercent = Math.round(carbonSavingsPercent * 10.0) / 10.0;
        this.rationale = rationale;
    }

    public int getCurrentReplicas() { return currentReplicas; }
    public void setCurrentReplicas(int currentReplicas) { this.currentReplicas = currentReplicas; }

    public int getRecommendedReplicas() { return recommendedReplicas; }
    public void setRecommendedReplicas(int recommendedReplicas) { this.recommendedReplicas = recommendedReplicas; }

    public double getCurrentCarbonKgPerHour() { return currentCarbonKgPerHour; }
    public void setCurrentCarbonKgPerHour(double currentCarbonKgPerHour) { 
        this.currentCarbonKgPerHour = Math.round(currentCarbonKgPerHour * 1000.0) / 1000.0; 
    }

    public double getProjectedCarbonKgPerHour() { return projectedCarbonKgPerHour; }
    public void setProjectedCarbonKgPerHour(double projectedCarbonKgPerHour) { 
        this.projectedCarbonKgPerHour = Math.round(projectedCarbonKgPerHour * 1000.0) / 1000.0; 
    }

    public String getCarbonOptimizedRegion() { return carbonOptimizedRegion; }
    public void setCarbonOptimizedRegion(String carbonOptimizedRegion) { this.carbonOptimizedRegion = carbonOptimizedRegion; }

    public double getCleanRegionCarbonKgPerHour() { return cleanRegionCarbonKgPerHour; }
    public void setCleanRegionCarbonKgPerHour(double cleanRegionCarbonKgPerHour) { 
        this.cleanRegionCarbonKgPerHour = Math.round(cleanRegionCarbonKgPerHour * 1000.0) / 1000.0; 
    }

    public double getCarbonSavingsPercent() { return carbonSavingsPercent; }
    public void setCarbonSavingsPercent(double carbonSavingsPercent) { 
        this.carbonSavingsPercent = Math.round(carbonSavingsPercent * 10.0) / 10.0; 
    }

    public String getRationale() { return rationale; }
    public void setRationale(String rationale) { this.rationale = rationale; }

    public String getStudentUsn() { return studentUsn; }
    public void setStudentUsn(String studentUsn) { this.studentUsn = studentUsn; }
}
