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
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;

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

//    public List<PersonInfosDTO> getPersonInfoFromLastName(String lastName) {
//        List<Person> persons =  personRepository.findByLastName(lastName);
//        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByLastName(lastName);
//
//        return dataMapper.personsAndMedicalRecordsToPersonDTO(persons, medicalRecords);
//    }
//
//    public List<ChildDTO> getChildrenFromAddress(String address) {
//        List<Person> persons = personRepository.findByAddress(address);
//
//        List<String> lastNames = new ArrayList<>();
//        persons.forEach(person -> {
//            String lastName = person.getLastName();
//            if (!lastNames.contains(lastName)) {
//                lastNames.add(lastName);
//            }
//        });
//
//        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByLastNameIn(lastNames);
//
//        return dataMapper.personsAndMedicalRecordsToChildDTO(persons, medicalRecords);
//    }
//
//    public List<String> getCommunityEmailsByCity(String city) {
//        List<Person> persons = personRepository.findByCity(city);
//
//        return persons.stream().map(Person::getEmail).distinct().toList();
//    }
}
