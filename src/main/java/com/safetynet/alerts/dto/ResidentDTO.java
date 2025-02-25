package com.safetynet.alerts.dto;

import java.util.List;

public record ResidentDTO(
        String firstName,
        String lastName,
        String phone,
        String age,
        List<String> medications,
        List<String> allergies
        ) {
}
