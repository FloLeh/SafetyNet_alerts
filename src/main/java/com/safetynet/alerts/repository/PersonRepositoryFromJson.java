package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PersonRepositoryFromJson implements PersonRepository {

    private final DataParser dataParser;

    public List<Person> findAll(){
        return new ArrayList<>(dataParser.getPersons());
    }

    public Optional<Person> findByFirstNameAndLastName(String firstName, String lastName) {
        return findAll()
                .stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst();
    }

    public List<Person> findByLastName(String lastName) {
        return List.of();
    }

    public List<Person> findByAddress(String address) {
        return List.of();
    }

    public List<Person> findByAddressIn(List<String> addresses) {
        return List.of();
    }

    public List<Person> findByCity(String city) {
        return List.of();
    }

    public void delete(Optional<Person> person) {
        dataParser.getPersons().remove(person.get());
    }

    public Person save(Person person) {
        dataParser.getPersons().add(person);
        return person;
    }

    public Person update(Person person) {
        dataParser.getPersons()
                .stream()
                .filter(personToUpdate -> personToUpdate.getFirstName().equals(person.getFirstName()) && personToUpdate.getLastName().equals(person.getLastName()))
                .forEach(personToUpdate -> {
                        personToUpdate.setAddress(person.getAddress());
                        personToUpdate.setCity(person.getCity());
                        personToUpdate.setZip(person.getZip());
                        personToUpdate.setPhone(person.getPhone());
                        personToUpdate.setEmail(person.getEmail());
                });
        return person;
    }
}
