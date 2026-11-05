package com.rit.ecopulse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TelemetryController {

    @GetMapping("/api/v1/telemetry/green")
    public ResponseEntity<Map<String, Object>> getGreenTelemetry() {
        Runtime runtime = Runtime.getRuntime();
        long totalMem = runtime.totalMemory() / (1024 * 1024);
        long freeMem = runtime.freeMemory() / (1024 * 1024);
        long usedMem = totalMem - freeMem;
        int processors = runtime.availableProcessors();

        // Pod power telemetry estimate: base container wattage ~ 3W + 0.05W per MB RAM used
        double estimatedPodWatts = 3.0 + (usedMem * 0.05);

        Map<String, Object> data = new HashMap<>();
        data.put("service", "EcoPulse AIOps Telemetry Agent");
        data.put("studentUsn", "1MS24CI060");
        data.put("department", "CSE (Artificial Intelligence and Machine Learning)");
        data.put("course", "25CIAEC59");
        data.put("status", "HEALTHY");
        data.put("availableProcessors", processors);
        data.put("usedMemoryMb", usedMem);
        data.put("totalMemoryMb", totalMem);
        data.put("jvmUptimeSeconds", ManagementFactory.getRuntimeMXBean().getUptime() / 1000);
        data.put("estimatedPodPowerWatts", Math.round(estimatedPodWatts * 10.0) / 10.0);

        return ResponseEntity.ok(data);
    }
}
