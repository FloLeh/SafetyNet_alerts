package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

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
}
