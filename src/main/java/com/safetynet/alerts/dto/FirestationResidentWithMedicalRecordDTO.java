package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.util.List;

public record FirestationResidentWithMedicalRecordDTO(
        String firstName,
        String lastName,
        String phone,
        int age,
        List<String> medications,
        List<String> allergies
        ) {

        public FirestationResidentWithMedicalRecordDTO(Person person, MedicalRecord medicalRecord) {
                this(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getPhone(),
                        medicalRecord.getAge(),
                        medicalRecord.getMedications(),
                        medicalRecord.getAllergies()
                );
        }
}
