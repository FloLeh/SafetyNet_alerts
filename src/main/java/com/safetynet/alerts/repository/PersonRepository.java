package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByLastName(String lastName);
    List<Person> findByAddress(String address);
    List<Person> findByAddressIn(List<String> addresses);
}
