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

    public List<Person> getPersons() {
        return (List<Person>) personRepository.findAll();
    }

    public Person savePerson(Person input) {

        Person person = new Person();

        person.setFirstName(input.getFirstName());
        person.setLastName(input.getLastName());
        person.setAddress(input.getAddress());
        person.setCity(input.getCity());
        person.setZip(input.getZip());
        person.setPhone(input.getPhone());
        person.setEmail(input.getEmail());

        return personRepository.save(person);
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
}
