package com.rit.ecopulse.controller;

import com.rit.ecopulse.model.IncidentAssessmentRequest;
import com.rit.ecopulse.model.IncidentAssessmentResponse;
import com.rit.ecopulse.service.IncidentPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/aiops")
public class IncidentController {

    private final IncidentPredictionService incidentService;

    @Autowired
    public IncidentController(IncidentPredictionService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping("/assess")
    public ResponseEntity<IncidentAssessmentResponse> assessIncident(@RequestBody IncidentAssessmentRequest request) {
        IncidentAssessmentResponse response = incidentService.assessPodIncident(request);
        return ResponseEntity.ok(response);
    }
}
