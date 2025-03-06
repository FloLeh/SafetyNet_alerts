package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonWithMedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> getPersons();

    Person savePerson(final Person input) throws Exception;
    Person updatePerson(final Person input) throws Exception;
    void deletePerson(final String firstName, final String lastName) throws Exception;

    List<PersonWithMedicalRecordDTO> getPersonInfoFromLastName(String lastName);
    List<ChildDTO> getChildrenFromAddress(String address);

    List<String> getCommunityEmailsByCity(String city);
    List<Person> getByAddress(String address);
    List<Person> getByAddressIn(List<String> addresses);

    static ChildDTO createChildDTO(MedicalRecord medicalRecord, List<Person> persons) {
        final Person child = persons.stream().filter(person -> person.getFirstName().equals(medicalRecord.getFirstName())).findFirst().orElseThrow();
        final List<Person> householdMembers = persons.stream()
                .filter(person -> person.getLastName().equals(medicalRecord.getLastName()))
                .filter(person -> !person.getFirstName().equals(medicalRecord.getFirstName()))
                .toList();
        return new ChildDTO(child.getFirstName(), child.getLastName(), medicalRecord.getAge(), householdMembers);
    }
}
