package com.rit.ecopulse.controller;

import com.rit.ecopulse.model.EcoHealthResponse;
import com.rit.ecopulse.service.CarbonModelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final CarbonModelingService carbonService;
    private final long initTimeMillis = System.currentTimeMillis();

    @Autowired
    public HealthController(CarbonModelingService carbonService) {
        this.carbonService = carbonService;
    }

    @GetMapping({"/", "/health"})
    public ResponseEntity<EcoHealthResponse> getHealth() {
        long uptime = (System.currentTimeMillis() - initTimeMillis) / 1000;
        String region = carbonService.getDefaultRegion();
        double intensity = carbonService.getGridIntensity(region);

        EcoHealthResponse response = new EcoHealthResponse("UP", region, intensity, uptime);
        return ResponseEntity.ok(response);
    }
}
