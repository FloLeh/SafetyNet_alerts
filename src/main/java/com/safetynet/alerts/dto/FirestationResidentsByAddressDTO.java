package com.safetynet.alerts.dto;

import java.util.List;

public record FirestationResidentsByAddressDTO(
        List<FirestationResidentWithMedicalRecordDTO> residents,
        int station
) {
}
