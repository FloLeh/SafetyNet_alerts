package com.safetynet.alerts.service;

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

    public Iterable<Person> getPersons() {
        return personRepository.findAll();
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
        Iterable<Person> persons =  personRepository.findByLastName(lastName);
        Iterable<MedicalRecord> medicalRecords = medicalRecordRepository.findByLastName(lastName);

        return (List<PersonInfosDTO>) dataMapper.personsAndMedicalRecordsToPersonDTO(persons, medicalRecords);
    }

    public List<Person> getByAddress(String address) {
        return (List<Person>) personRepository.findByAddress(address);
    }
}
