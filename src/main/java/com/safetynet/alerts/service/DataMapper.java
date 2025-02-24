package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildDTO;
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

    public List<PersonInfosDTO> personsAndMedicalRecordsToPersonDTO(Iterable<Person> persons, Iterable<MedicalRecord> medicalRecords) {

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

    public List<ChildDTO> personsAndMedicalRecordsToChildDTO(List<Person> persons, List<MedicalRecord> medicalRecords) {
        List<PersonInfosDTO> personInfosDTOS = personsAndMedicalRecordsToPersonDTO(persons, medicalRecords);

        List<PersonInfosDTO> children = personInfosDTOS.stream()
                .filter(person -> person.age() < 18)
                .toList();

        List<ChildDTO> childrenDTO = new ArrayList<>();
        for (PersonInfosDTO child : children) {
            List<Person> householdMembers = persons.stream()
                    .filter(person ->
                        person.getAddress().equals(child.address())
                                && !(person.getFirstName().equals(child.firstName())
                                    && person.getLastName().equals(child.lastName()))
                    ).toList();

            childrenDTO.add(new ChildDTO(
                    child.firstName(),
                    child.lastName(),
                    child.age(),
                    householdMembers
            ));
        }

        return childrenDTO;
    }

    public int computeAgeFromBirthdate(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        return Period.between(LocalDate.parse(birthdate, formatter), LocalDate.now()).getYears();
    }

}
