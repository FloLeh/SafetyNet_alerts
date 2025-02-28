package com.safetynet.alerts.dto;

import java.util.Collection;

public record ResidentDTO(
        String firstName,
        String lastName,
        String phone,
        String age,
        Collection<String> medications,
        Collection<String> allergies
        ) {
}
