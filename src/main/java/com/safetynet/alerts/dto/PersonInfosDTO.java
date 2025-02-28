package com.safetynet.alerts.dto;

import java.util.Collection;

public record PersonInfosDTO(
        String firstName,
        String lastName,
        String address,
        String age,
        String email,
        Collection<String> medications,
        Collection<String> allergies
) {}
