package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository {
    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);
    List<Person> findByLastName(String lastName);
    List<Person> findByAddress(String address);
    List<Person> findByAddressIn(List<String> addresses);
    List<Person> findByCity(String city);

    void delete(Optional<Person> person);
    Person save(Person person);
    Person update(Person person);

    Collection<Person> findAll();
}
