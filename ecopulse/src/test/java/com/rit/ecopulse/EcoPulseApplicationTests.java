package com.rit.ecopulse;

import com.rit.ecopulse.controller.CarbonController;
import com.rit.ecopulse.controller.HealthController;
import com.rit.ecopulse.controller.IncidentController;
import com.rit.ecopulse.controller.TelemetryController;
import com.rit.ecopulse.model.*;
import com.rit.ecopulse.service.CarbonModelingService;
import com.rit.ecopulse.service.IncidentPredictionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EcoPulseApplicationTests {

    private IncidentPredictionService incidentService;
    private CarbonModelingService carbonService;
    private HealthController healthController;
    private IncidentController incidentController;
    private CarbonController carbonController;
    private TelemetryController telemetryController;

    @BeforeEach
    void setUp() {
        incidentService = new IncidentPredictionService();
        carbonService = new CarbonModelingService();
        healthController = new HealthController(carbonService);
        incidentController = new IncidentController(incidentService);
        carbonController = new CarbonController(carbonService);
        telemetryController = new TelemetryController();
    }

    @Test
    @DisplayName("Health endpoint returns UP and student USN 1MS24CI060")
    void testHealthEndpointReturnsUpAndStudentUSN() {
        ResponseEntity<EcoHealthResponse> response = healthController.getHealth();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        EcoHealthResponse body = response.getBody();
        assertEquals("UP", body.getStatus());
        assertEquals("1MS24CI060", body.getStudentUsn());
        assertEquals("CSE (Artificial Intelligence and Machine Learning)", body.getDepartment());
        // guide assertion omitted
        assertEquals("ecopulse-aiops", body.getService());
    }

    @Test
    @DisplayName("AIOps incident detector predicts CPU starvation and OOM incidents")
    void testIncidentPredictionDetectsCpuStarvation() {
        IncidentAssessmentRequest req = new IncidentAssessmentRequest(
                "ecopulse-worker-pod-3", "production", 82.0, 91.0, 4.0, 8.5, 3
        );

        ResponseEntity<IncidentAssessmentResponse> response = incidentController.assessIncident(req);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        IncidentAssessmentResponse body = response.getBody();
        assertEquals("1MS24CI060", body.getStudentUsn());
        assertTrue(body.getFailureRiskScore() >= 0.70);
        assertEquals("OOM_KILL_IMMOBILE", body.getIncidentAlert());
        assertFalse(body.getRiskFactors().isEmpty());
    }

    @Test
    @DisplayName("Carbon modeling service calculates compute energy and grid emissions")
    void testCarbonModelingEstimatesWorkloadEnergyAndCarbon() {
        CarbonEstimateRequest req = new CarbonEstimateRequest(
                "ecopulse-k8s-pod-workload", 4, 16.0, 24.0, "AWS", "ap-south-1", 0.75
        );

        ResponseEntity<CarbonEstimateResponse> response = carbonController.estimateCarbon(req);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        CarbonEstimateResponse body = response.getBody();
        assertEquals("1MS24CI060", body.getStudentUsn());
        assertTrue(body.getEnergyConsumptionKwh() > 0.0);
        assertTrue(body.getCarbonEmissionsKgCo2() > 0.0);
        assertEquals("C_CARBON_INTENSIVE", body.getEfficiencyTier());
    }

    @Test
    @DisplayName("Autoscaling optimizer recommends scaling from 3 to 5 replicas under load")
    void testGreenAutoscalingRecommendationRecommendsScalingToFive() {
        ScalingRecommendationRequest req = new ScalingRecommendationRequest(
                3, 1600.0, 0.85, "ap-south-1"
        );

        ResponseEntity<ScalingRecommendationResponse> response = carbonController.optimizeScaling(req);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        ScalingRecommendationResponse body = response.getBody();
        assertEquals("1MS24CI060", body.getStudentUsn());
        assertEquals(3, body.getCurrentReplicas());
        assertEquals(5, body.getRecommendedReplicas(), "Should recommend scaling out to 5 replicas under heavy load");
        assertEquals("eu-north-1", body.getCarbonOptimizedRegion());
        assertTrue(body.getCarbonSavingsPercent() > 80.0, "Clean Sweden grid should save > 80% carbon vs Mumbai");
    }

    @Test
    @DisplayName("Green telemetry endpoint exposes JVM stats and container power estimate")
    void testGreenTelemetryEndpoint() {
        ResponseEntity<Map<String, Object>> response = telemetryController.getGreenTelemetry();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        Map<String, Object> data = response.getBody();
        assertEquals("1MS24CI060", data.get("studentUsn"));
        assertEquals("HEALTHY", data.get("status"));
        assertTrue((int) data.get("availableProcessors") > 0);
        assertNotNull(data.get("estimatedPodPowerWatts"));
    }
}
