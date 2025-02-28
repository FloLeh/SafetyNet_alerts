package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.FirestationResidentsDTO;
import com.safetynet.alerts.dto.PersonInfosDTO;
import com.safetynet.alerts.dto.ResidentDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
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

    public Collection<PersonInfosDTO> personsAndMedicalRecordsToPersonDTO(Iterable<Person> persons, Iterable<MedicalRecord> medicalRecords) {

        Collection<PersonInfosDTO> personsInfosDTO = new ArrayList<>();

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

    public Collection<ChildDTO> personsAndMedicalRecordsToChildDTO(Collection<Person> persons, Collection<MedicalRecord> medicalRecords) {
        Collection<PersonInfosDTO> personInfosDTOS = personsAndMedicalRecordsToPersonDTO(persons, medicalRecords);

        Collection<PersonInfosDTO> children = personInfosDTOS.stream()
                .filter(person -> Integer.parseInt(person.age()) <= 18)
                .toList();

        Collection<ChildDTO> childrenDTO = new ArrayList<>();
        for (PersonInfosDTO child : children) {
            Collection<Person> householdMembers = persons.stream()
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

    public Map<String, Object> personWithMedicalRecordAndFirestationNumber(Collection<Person> persons, Collection<MedicalRecord> medicalRecords, int station) {
        Collection<ResidentDTO> residents = residentsByMedicalRecord(persons, medicalRecords);
        return Map.of(
                "residents", residents,
                "station", station
        );
    }

    public Map<String, Collection<ResidentDTO>> housesByFirestations(Collection<Person> persons, Collection<MedicalRecord> medicalRecords, Collection<Firestation> fireStations) {
        Map<String, Collection<ResidentDTO>> residentsByAddress = new HashMap<>();
        Collection<String> addresses = fireStations.stream().map(Firestation::getAddress).toList();

        for (String address : addresses) {
            Collection<Person> personsFiltered = persons.stream().filter(person -> person.getAddress().equals(address)).toList();
            Collection<ResidentDTO> residents = residentsByMedicalRecord(personsFiltered, medicalRecords);

            residentsByAddress.put(address, residents);
        }

        return residentsByAddress;
    }

    public Collection<ResidentDTO> residentsByMedicalRecord(Collection<Person> persons, Collection<MedicalRecord> medicalRecords) {
        Collection<ResidentDTO> residents = new ArrayList<>();
        for (Person person : persons) {
            String firstName = person.getFirstName();
            String lastName = person.getLastName();
            String phone = person.getPhone();

            for (MedicalRecord medicalRecord : medicalRecords) {
                if (person.getLastName().equals(medicalRecord.getLastName()) &&
                        person.getFirstName().equals(medicalRecord.getFirstName())
                ) {
                    String age = computeAgeFromBirthdate(medicalRecord.getBirthdate());
                    Collection<String> medications = medicalRecord.getMedications();
                    Collection<String> allergies = medicalRecord.getAllergies();
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

    public Collection<FirestationResidentsDTO> personsToFirestationDTO(Collection<Person> persons) {
        Collection<FirestationResidentsDTO> fireStationResidentsDTOs = new ArrayList<>();
        for (Person person : persons) {
            fireStationResidentsDTOs.add(new FirestationResidentsDTO(
                    person.getFirstName(),
                    person.getLastName(),
                    person.getAddress(),
                    person.getPhone()
            ));
        }
        return fireStationResidentsDTOs;
    }

    public Map<String, Object> residentsByFirestation(Collection<Person> persons, Collection<MedicalRecord> medicalRecords) {
        Collection<FirestationResidentsDTO> residents = personsToFirestationDTO(persons);
        int adults = medicalRecords.stream().filter(medicalRecord -> Integer.parseInt(computeAgeFromBirthdate(medicalRecord.getBirthdate())) > 18).toList().size();
        int children = medicalRecords.size() - adults;

        return Map.of(
                "residents", residents,
                "adults", adults,
                "children", children
        );
    }

    public String computeAgeFromBirthdate(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        return String.valueOf(Period.between(LocalDate.parse(birthdate, formatter), LocalDate.now()).getYears());
    }
}
