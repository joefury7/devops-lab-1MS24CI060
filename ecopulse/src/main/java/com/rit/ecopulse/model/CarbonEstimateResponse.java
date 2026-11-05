package com.rit.ecopulse.model;

public class CarbonEstimateResponse {
    private String workloadName;
    private double energyConsumptionKwh;
    private double carbonEmissionsKgCo2;
    private double gridCarbonIntensity;
    private double pue;
    private String efficiencyTier; // A_ECO_OPTIMAL, B_MODERATE, C_CARBON_INTENSIVE
    private String studentUsn = "1MS24CI060";
    private long latencyMs;

    public CarbonEstimateResponse() {}

    public CarbonEstimateResponse(String workloadName, double energyConsumptionKwh, 
                                  double carbonEmissionsKgCo2, double gridCarbonIntensity, 
                                  double pue, String efficiencyTier, long latencyMs) {
        this.workloadName = workloadName;
        this.energyConsumptionKwh = Math.round(energyConsumptionKwh * 1000.0) / 1000.0;
        this.carbonEmissionsKgCo2 = Math.round(carbonEmissionsKgCo2 * 1000.0) / 1000.0;
        this.gridCarbonIntensity = gridCarbonIntensity;
        this.pue = pue;
        this.efficiencyTier = efficiencyTier;
        this.latencyMs = latencyMs;
    }

    public String getWorkloadName() { return workloadName; }
    public void setWorkloadName(String workloadName) { this.workloadName = workloadName; }

    public double getEnergyConsumptionKwh() { return energyConsumptionKwh; }
    public void setEnergyConsumptionKwh(double energyConsumptionKwh) { 
        this.energyConsumptionKwh = Math.round(energyConsumptionKwh * 1000.0) / 1000.0; 
    }

    public double getCarbonEmissionsKgCo2() { return carbonEmissionsKgCo2; }
    public void setCarbonEmissionsKgCo2(double carbonEmissionsKgCo2) { 
        this.carbonEmissionsKgCo2 = Math.round(carbonEmissionsKgCo2 * 1000.0) / 1000.0; 
    }

    public double getGridCarbonIntensity() { return gridCarbonIntensity; }
    public void setGridCarbonIntensity(double gridCarbonIntensity) { this.gridCarbonIntensity = gridCarbonIntensity; }

    public double getPue() { return pue; }
    public void setPue(double pue) { this.pue = pue; }

    public String getEfficiencyTier() { return efficiencyTier; }
    public void setEfficiencyTier(String efficiencyTier) { this.efficiencyTier = efficiencyTier; }

    public String getStudentUsn() { return studentUsn; }
    public void setStudentUsn(String studentUsn) { this.studentUsn = studentUsn; }

    public long getLatencyMs() { return latencyMs; }
    public void setLatencyMs(long latencyMs) { this.latencyMs = latencyMs; }
}
