package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface PersonRepository {
    List<Person> findAll();

    Person save(Person person) throws IOException;
    Person update(Person person) throws IOException;
    void delete(Person person) throws IOException;

    Person findByFirstNameAndLastName(String firstName, String lastName);
    List<Person> findByLastName(String lastName);
    List<Person> findByAddress(String address);
    List<Person> findByCity(String city);
}
