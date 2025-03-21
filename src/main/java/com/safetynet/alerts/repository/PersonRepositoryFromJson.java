package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PersonRepositoryFromJson implements PersonRepository {

    private final DataParser dataParser;

    public List<Person> findAll(){
        return new ArrayList<>(dataParser.getPersons());
    }

    public Person findByFirstNameAndLastName(String firstName, String lastName) {
        return findAll()
                .stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid firstName or lastName"));
    }

    public List<Person> findByLastName(String lastName) {
        return dataParser.getPersons()
                .stream()
                .filter(person -> person.getLastName().equals(lastName))
                .toList();
    }

    public List<Person> findByAddress(String address) {
        return dataParser.getPersons()
                .stream()
                .filter(person -> person.getAddress().equals(address))
                .toList();
    }

    public List<Person> findByAddressIn(List<String> addresses) {
        return dataParser.getPersons()
                .stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .toList();
    }

    public List<Person> findByCity(String city) {
        return dataParser.getPersons()
                .stream()
                .filter(person -> person.getCity().equals(city))
                .toList();
    }

    public Person save(Person person) throws IOException {
        dataParser.getPersons().add(person);
        dataParser.save();
        return person;
    }

    public Person update(Person person) throws IOException {
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
        dataParser.save();
        return person;
    }

    public void delete(Person person) throws IOException {
        dataParser.getPersons().remove(person);
        dataParser.save();
    }


}
