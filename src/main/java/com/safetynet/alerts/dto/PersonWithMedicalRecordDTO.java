package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.util.List;

public record PersonWithMedicalRecordDTO(
        String firstName,
        String lastName,
        String address,
        Integer age,
        String email,
        List<String> medications,
        List<String> allergies
) {
    public PersonWithMedicalRecordDTO(Person person, MedicalRecord medicalRecord){
        this(
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                medicalRecord.getAge(),
                person.getEmail(),
                medicalRecord.getMedications(),
                medicalRecord.getAllergies()
        );
    }


}
