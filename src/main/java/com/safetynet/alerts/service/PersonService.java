package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonInfosDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private DataMapper dataMapper;

    public Person savePerson(Person input) {
        return personRepository.save(input);
    }

    public Person updatePerson(Person input) {
        Person person = personRepository.findByFirstNameAndLastName(input.getFirstName(), input.getLastName());
        if (person != null) {
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

            return personRepository.save(person);
        }
        return null;
    }

    public void deletePerson(String firstName, String lastName) {
        Person person = personRepository.findByFirstNameAndLastName(firstName, lastName);
        personRepository.delete(person);
    }

    public List<PersonInfosDTO> getPersonInfoFromLastName(String lastName) {
        List<Person> persons =  personRepository.findByLastName(lastName);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByLastName(lastName);

        return dataMapper.personsAndMedicalRecordsToPersonDTO(persons, medicalRecords);
    }

    public List<ChildDTO> getChildrenFromAddress(String address) {
        List<Person> persons = personRepository.findByAddress(address);

        List<String> lastNames = new ArrayList<>();
        persons.forEach(person -> {
            String lastName = person.getLastName();
            if (!lastNames.contains(lastName)) {
                lastNames.add(lastName);
            }
        });

        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByLastNameIn(lastNames);

        return dataMapper.personsAndMedicalRecordsToChildDTO(persons, medicalRecords);
    }

    public List<String> getCommunityEmailsByCity(String city) {
        List<Person> persons = personRepository.findByCity(city);

        return persons.stream().map(Person::getEmail).distinct().toList();
    }
}
