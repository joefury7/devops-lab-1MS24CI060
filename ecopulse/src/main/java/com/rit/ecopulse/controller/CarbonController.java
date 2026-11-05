package com.rit.ecopulse.controller;

import com.rit.ecopulse.model.*;
import com.rit.ecopulse.service.CarbonModelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarbonController {

    private final CarbonModelingService carbonService;

    @Autowired
    public CarbonController(CarbonModelingService carbonService) {
        this.carbonService = carbonService;
    }

    @PostMapping("/api/v1/carbon/estimate")
    public ResponseEntity<CarbonEstimateResponse> estimateCarbon(@RequestBody CarbonEstimateRequest request) {
        CarbonEstimateResponse response = carbonService.estimateWorkload(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/aiops/optimize-scaling")
    public ResponseEntity<ScalingRecommendationResponse> optimizeScaling(@RequestBody ScalingRecommendationRequest request) {
        ScalingRecommendationResponse response = carbonService.optimizeScaling(request);
        return ResponseEntity.ok(response);
    }
}
