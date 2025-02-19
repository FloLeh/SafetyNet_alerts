package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Data
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Iterable<Person> getPersons() {
        return personRepository.findAll();
    }

    public Person savePerson(HashMap<String, String> input) {

        Person person = new Person();

        person.setFirstName(input.get("firstName"));
        person.setLastName(input.get("lastName"));
        person.setAddress(input.get("address"));
        person.setCity(input.get("city"));
        person.setZip(input.get("zip"));
        person.setPhone(input.get("phone"));
        person.setEmail(input.get("email"));

        return personRepository.save(person);
    }
}
