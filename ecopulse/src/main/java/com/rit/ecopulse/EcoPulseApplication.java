package com.rit.ecopulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcoPulseApplication {

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("  EcoPulse AIOps: Green K8s Telemetry & Incident Mitigation Engine");
        System.out.println("  Student USN: 1MS24CI060 | Dept: CSE (AI & ML) | RIT Bangalore  ");
        System.out.println("==================================================================");
        SpringApplication.run(EcoPulseApplication.class, args);
    }
}
