package com.safetynet.alerts.dto;

import java.util.List;

public record FirestationResidentsCountDTO(
        List<FirestationResidentDTO> residents,
        int adults,
        int children
) {
}
