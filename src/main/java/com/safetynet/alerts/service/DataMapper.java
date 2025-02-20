package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class DataMapper {

    public PersonDTO personAndMedicalRecordToPersonDTO(Person person, MedicalRecord medicalRecord) {
        return new PersonDTO(
                person.getFirstName() + " " + person.getLastName(),
                person.getAddress(),
                computeAgeFromMedicalRecord(medicalRecord),
                person.getEmail(),
                medicalRecord.getMedications(),
                medicalRecord.getAllergies()
        );
    }

    private int computeAgeFromMedicalRecord(MedicalRecord medicalRecord) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String birthDate = medicalRecord.getBirthdate();

        return Period.between(LocalDate.parse(birthDate, formatter), LocalDate.now()).getYears();
    }

}
