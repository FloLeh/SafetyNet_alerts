package com.safetynet.alerts.dto;

import java.util.List;

public record PersonDTO(
        String fullName,
        String address,
        int age,
        String email,
        List<String> medications,
        List<String> allergies
) {}
