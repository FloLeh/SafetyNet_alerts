package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfosDTO;
import com.safetynet.alerts.dto.FireStationResidentsDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataMapper {

    public PersonInfosDTO personAndMedicalRecordToPersonDTO(Person person, MedicalRecord medicalRecord) {
        return new PersonInfosDTO(
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                computeAgeFromBirthdate(medicalRecord.getBirthdate()),
                person.getEmail(),
                medicalRecord.getMedications(),
                medicalRecord.getAllergies()
        );
    }

    public Iterable<PersonInfosDTO> personsAndMedicalRecordsToPersonDTO(Iterable<Person> persons, Iterable<MedicalRecord> medicalRecords) {

        List<PersonInfosDTO> personsInfosDTO = new ArrayList<>();

        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (person.getLastName().equals(medicalRecord.getLastName()) &&
                        person.getFirstName().equals(medicalRecord.getFirstName())
                ) {
                    personsInfosDTO.add(personAndMedicalRecordToPersonDTO(person, medicalRecord));
                }
            }
        }

        return personsInfosDTO;
    }

    public FireStationResidentsDTO personToFireStationResidentDTO(Person person) {
        return new FireStationResidentsDTO(
          person.getFirstName(),
          person.getLastName(),
          person.getAddress(),
          person.getPhone()
        );
    }

    public int computeAgeFromBirthdate(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        return Period.between(LocalDate.parse(birthdate, formatter), LocalDate.now()).getYears();
    }

}
