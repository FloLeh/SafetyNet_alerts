package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonInfosDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final DataMapper dataMapper;
    private final MedicalRecordService medicalRecordService;
    private final DataParser dataParser;

    public Collection<Person> getPersons() {
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

    public Collection<PersonInfosDTO> getPersonInfoFromLastName(String lastName) {
        Collection<Person> persons =  personRepository.findByLastName(lastName);
        Collection<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordByLastName(lastName);

        return dataMapper.personsAndMedicalRecordsToPersonDTO(persons, medicalRecords);
    }

    public Collection<ChildDTO> getChildrenFromAddress(String address) {
        Collection<Person> persons = personRepository.findByAddress(address);

        Collection<String> lastNames = new ArrayList<>();
        persons.forEach(person -> {
            String lastName = person.getLastName();
            if (!lastNames.contains(lastName)) {
                lastNames.add(lastName);
            }
        });

        Collection<MedicalRecord> medicalRecords = medicalRecordService.getByLastNameIn(lastNames);

        return dataMapper.personsAndMedicalRecordsToChildDTO(persons, medicalRecords);
    }

    public Collection<String> getCommunityEmailsByCity(String city) {
        Collection<Person> persons = personRepository.findByCity(city);

        return persons.stream().map(Person::getEmail).distinct().toList();
    }

    public Collection<Person> getByAddress(String address) {
        return dataParser.getPersons()
                .stream()
                .filter(person -> person.getAddress().equals(address))
                .toList();
    }

    public Collection<Person> getByAddressIn(Collection<String> addresses) {
        return dataParser.getPersons()
                .stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .toList();
    }
}
