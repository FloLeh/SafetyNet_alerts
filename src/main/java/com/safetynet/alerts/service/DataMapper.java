package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonInfosDTO;
import com.safetynet.alerts.dto.ResidentDTO;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .filter(person -> Integer.parseInt(person.age()) < 18)
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

    public Map<String, Object> personWithMedicalRecordAndFireStationNumber(List<Person> persons, List<MedicalRecord> medicalRecords, String station) {
        List<ResidentDTO> residents = residentsByMedicalRecord(persons, medicalRecords);
        return Map.of(
                "residents", residents,
                "station", station
        );
    }

    public Map<String, List<ResidentDTO>> residentsByFireStation(List<Person> persons, List<MedicalRecord> medicalRecords, List<FireStation> fireStations) {
        Map<String, List<ResidentDTO>> residentsByAddress = new HashMap<>();
        List<String> addresses = fireStations.stream().map(FireStation::getAddress).toList();

        for (String address : addresses) {
            List<Person> personsFiltered = persons.stream().filter(person -> person.getAddress().equals(address)).toList();
            List<ResidentDTO> residents = residentsByMedicalRecord(personsFiltered, medicalRecords);

            residentsByAddress.put(address, residents);
        }

        return residentsByAddress;
    }

    public List<ResidentDTO> residentsByMedicalRecord(List<Person> persons, List<MedicalRecord> medicalRecords) {
        List<ResidentDTO> residents = new ArrayList<>();
        for (Person person : persons) {
            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            String phone = person.getPhone();

            for (MedicalRecord medicalRecord : medicalRecords) {
                if (person.getLastName().equals(medicalRecord.getLastName()) &&
                        person.getFirstName().equals(medicalRecord.getFirstName())
                ) {
                    String age = computeAgeFromBirthdate(medicalRecord.getBirthdate());
                    List<String> medications = medicalRecord.getMedications();
                    List<String> allergies = medicalRecord.getAllergies();
                    residents.add(new ResidentDTO(
                            firstName,
                            lastName,
                            phone,
                            age,
                            medications,
                            allergies
                    ));
                }
            }
        }
        return residents;
    }

    public String computeAgeFromBirthdate(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        return String.valueOf(Period.between(LocalDate.parse(birthdate, formatter), LocalDate.now()).getYears());
    }

}
