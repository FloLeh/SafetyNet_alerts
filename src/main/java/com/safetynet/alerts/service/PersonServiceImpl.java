package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonWithMedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final MedicalRecordServiceImpl medicalRecordService;
    private final DataParser dataParser;

    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public Person savePerson(final Person input) throws IOException {
        return personRepository.save(input);
    }

    public Person updatePerson(final Person input) throws IOException {
        Assert.hasText(input.getFirstName(), "firstName is required");
        Assert.hasText(input.getLastName(), "lastName is required");

        Person person = personRepository.findByFirstNameAndLastName(input.getFirstName(), input.getLastName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid firstName or lastName"));

        if (input.getAddress() != null) {
            person.setAddress(input.getAddress());
        }
        if (input.getCity() != null) {
            person.setCity(input.getCity());
        }
        if (input.getZip() != null) {
            person.setZip(input.getZip());
        }
        if (input.getPhone() != null) {
            person.setPhone(input.getPhone());
        }
        if (input.getEmail() != null) {
            person.setEmail(input.getEmail());
        }

        return personRepository.update(person);
    }

    public void deletePerson(final String firstName, final String lastName) throws IOException {
        Optional<Person> person = personRepository.findByFirstNameAndLastName(firstName, lastName);
        personRepository.delete(person);
    }

    public List<PersonWithMedicalRecordDTO> getPersonInfoFromLastName(String lastName) {
        List<Person> persons =  personRepository.findByLastName(lastName);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordByLastName(lastName);

        return persons.stream().map(person -> {
            final MedicalRecord medicalRecord = medicalRecords.stream()
                    .filter(m -> m.getFirstName().equals(person.getFirstName()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("No medical record found for " + person.getFirstName()));
            return new PersonWithMedicalRecordDTO(person, medicalRecord);
        }).toList();
    }

    public List<ChildDTO> getChildrenFromAddress(String address) {
        List<Person> persons = personRepository.findByAddress(address);

        List<String> lastNames = persons.stream()
                .map(Person::getLastName)
                .distinct()
                .toList();

        List<MedicalRecord> medicalRecords = medicalRecordService.getByLastNameIn(lastNames);

        return medicalRecords.stream()
                .filter(MedicalRecord::isMinor)
                .map( medicalRecord -> PersonService.createChildDTO(medicalRecord, persons))
                .toList();
    }

    public List<String> getCommunityEmailsByCity(String city) {
        List<Person> persons = personRepository.findByCity(city);

        return persons.stream().map(Person::getEmail).distinct().toList();
    }

    public List<Person> getByAddress(String address) {
        return dataParser.getPersons()
                .stream()
                .filter(person -> person.getAddress().equals(address))
                .toList();
    }

    public List<Person> getByAddressIn(List<String> addresses) {
        return dataParser.getPersons()
                .stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .toList();
    }
}
