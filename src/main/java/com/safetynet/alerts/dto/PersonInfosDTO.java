package com.safetynet.alerts.dto;

import java.util.List;

public record PersonInfosDTO(
        String firstName,
        String lastName,
        String address,
        String age,
        String email,
        List<String> medications,
        List<String> allergies
) {}
