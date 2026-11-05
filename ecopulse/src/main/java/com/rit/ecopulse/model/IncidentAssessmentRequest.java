package com.rit.ecopulse.model;

public class IncidentAssessmentRequest {
    private String podName = "ecopulse-worker-0";
    private String namespace = "production";
    private double cpuThrottlingPercentage = 15.0; // % throttled
    private double memoryUsagePercent = 65.0;       // % memory used
    private double memoryGrowthRateMbPerSec = 0.5;  // MB/sec slope
    private double http5xxErrorRate = 0.5;          // % 5xx responses
    private int restartCount = 0;

    public IncidentAssessmentRequest() {}

    public IncidentAssessmentRequest(String podName, String namespace, double cpuThrottlingPercentage, 
                                     double memoryUsagePercent, double memoryGrowthRateMbPerSec, 
                                     double http5xxErrorRate, int restartCount) {
        this.podName = podName;
        this.namespace = namespace;
        this.cpuThrottlingPercentage = cpuThrottlingPercentage;
        this.memoryUsagePercent = memoryUsagePercent;
        this.memoryGrowthRateMbPerSec = memoryGrowthRateMbPerSec;
        this.http5xxErrorRate = http5xxErrorRate;
        this.restartCount = restartCount;
    }

    public String getPodName() { return podName; }
    public void setPodName(String podName) { this.podName = podName; }

    public String getNamespace() { return namespace; }
    public void setNamespace(String namespace) { this.namespace = namespace; }

    public double getCpuThrottlingPercentage() { return cpuThrottlingPercentage; }
    public void setCpuThrottlingPercentage(double cpuThrottlingPercentage) { this.cpuThrottlingPercentage = cpuThrottlingPercentage; }

    public double getMemoryUsagePercent() { return memoryUsagePercent; }
    public void setMemoryUsagePercent(double memoryUsagePercent) { this.memoryUsagePercent = memoryUsagePercent; }

    public double getMemoryGrowthRateMbPerSec() { return memoryGrowthRateMbPerSec; }
    public void setMemoryGrowthRateMbPerSec(double memoryGrowthRateMbPerSec) { this.memoryGrowthRateMbPerSec = memoryGrowthRateMbPerSec; }

    public double getHttp5xxErrorRate() { return http5xxErrorRate; }
    public void setHttp5xxErrorRate(double http5xxErrorRate) { this.http5xxErrorRate = http5xxErrorRate; }

    public int getRestartCount() { return restartCount; }
    public void setRestartCount(int restartCount) { this.restartCount = restartCount; }
}
