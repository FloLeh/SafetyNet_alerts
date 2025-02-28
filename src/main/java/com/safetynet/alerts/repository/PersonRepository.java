package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface PersonRepository {
    Collection<Person> findAll();

    Person save(Person person) throws IOException;
    Person update(Person person) throws IOException;
    void delete(Optional<Person> person) throws IOException;

    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);
    Collection<Person> findByLastName(String lastName);
    Collection<Person> findByAddress(String address);
    Collection<Person> findByCity(String city);
}
