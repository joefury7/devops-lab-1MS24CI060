package com.rit.ecopulse.model;

public class CarbonEstimateRequest {
    private String workloadName = "k8s-ecopulse-cluster";
    private int vCpuCount = 4;
    private double memoryGb = 16.0;
    private double runtimeHours = 24.0;
    private String cloudProvider = "AWS"; // AWS, GCP, AZURE, ON_PREM
    private String cloudRegion = "ap-south-1"; // ap-south-1, eu-north-1, us-east-1
    private double averageCpuUtilization = 0.70;

    public CarbonEstimateRequest() {}

    public CarbonEstimateRequest(String workloadName, int vCpuCount, double memoryGb, 
                                 double runtimeHours, String cloudProvider, String cloudRegion, 
                                 double averageCpuUtilization) {
        this.workloadName = workloadName;
        this.vCpuCount = vCpuCount;
        this.memoryGb = memoryGb;
        this.runtimeHours = runtimeHours;
        this.cloudProvider = cloudProvider;
        this.cloudRegion = cloudRegion;
        this.averageCpuUtilization = averageCpuUtilization;
    }

    public String getWorkloadName() { return workloadName; }
    public void setWorkloadName(String workloadName) { this.workloadName = workloadName; }

    public int getvCpuCount() { return vCpuCount; }
    public void setvCpuCount(int vCpuCount) { this.vCpuCount = vCpuCount; }

    public double getMemoryGb() { return memoryGb; }
    public void setMemoryGb(double memoryGb) { this.memoryGb = memoryGb; }

    public double getRuntimeHours() { return runtimeHours; }
    public void setRuntimeHours(double runtimeHours) { this.runtimeHours = runtimeHours; }

    public String getCloudProvider() { return cloudProvider; }
    public void setCloudProvider(String cloudProvider) { this.cloudProvider = cloudProvider; }

    public String getCloudRegion() { return cloudRegion; }
    public void setCloudRegion(String cloudRegion) { this.cloudRegion = cloudRegion; }

    public double getAverageCpuUtilization() { return averageCpuUtilization; }
    public void setAverageCpuUtilization(double averageCpuUtilization) { this.averageCpuUtilization = averageCpuUtilization; }
}
