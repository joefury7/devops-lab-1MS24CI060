package com.rit.ecopulse.model;

import java.util.ArrayList;
import java.util.List;

public class IncidentAssessmentResponse {
    private String podName;
    private double failureRiskScore;
    private String incidentAlert; // HEALTHY, CPU_STARVATION_WARNING, OOM_KILL_IMMOBILE
    private List<String> riskFactors = new ArrayList<>();
    private String recommendedAction;
    private String studentUsn = "1MS24CI060";
    private long latencyMs;

    public IncidentAssessmentResponse() {}

    public IncidentAssessmentResponse(String podName, double failureRiskScore, String incidentAlert, 
                                      List<String> riskFactors, String recommendedAction, long latencyMs) {
        this.podName = podName;
        this.failureRiskScore = Math.round(failureRiskScore * 100.0) / 100.0;
        this.incidentAlert = incidentAlert;
        this.riskFactors = riskFactors;
        this.recommendedAction = recommendedAction;
        this.latencyMs = latencyMs;
    }

    public String getPodName() { return podName; }
    public void setPodName(String podName) { this.podName = podName; }

    public double getFailureRiskScore() { return failureRiskScore; }
    public void setFailureRiskScore(double failureRiskScore) { 
        this.failureRiskScore = Math.round(failureRiskScore * 100.0) / 100.0; 
    }

    public String getIncidentAlert() { return incidentAlert; }
    public void setIncidentAlert(String incidentAlert) { this.incidentAlert = incidentAlert; }

    public List<String> getRiskFactors() { return riskFactors; }
    public void setRiskFactors(List<String> riskFactors) { this.riskFactors = riskFactors; }

    public String getRecommendedAction() { return recommendedAction; }
    public void setRecommendedAction(String recommendedAction) { this.recommendedAction = recommendedAction; }

    public String getStudentUsn() { return studentUsn; }
    public void setStudentUsn(String studentUsn) { this.studentUsn = studentUsn; }

    public long getLatencyMs() { return latencyMs; }
    public void setLatencyMs(long latencyMs) { this.latencyMs = latencyMs; }
}
