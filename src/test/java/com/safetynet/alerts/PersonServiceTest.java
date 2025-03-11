package com.safetynet.alerts;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonWithMedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.MedicalRecordServiceImpl;
import com.safetynet.alerts.service.PersonServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordServiceImpl medicalRecordService;

    @InjectMocks
    private PersonServiceImpl personService;

    static List<Person> persons = new ArrayList<>();
    static List<MedicalRecord> medicalRecords = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Smith");
        person.setAddress("123 Main St");
        person.setCity("San Francisco");
        person.setZip("94105");
        person.setPhone("555-555-5555");
        person.setEmail("john.smith@gmail.com");
        persons.add(person);

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Smith");
        medicalRecord.setBirthdate(LocalDate.of(2010, 1 ,1));
        medicalRecord.setMedications(List.of("Doliprane"));
        medicalRecord.setAllergies(List.of("Peanuts"));
        medicalRecords.add(medicalRecord);
    }

    @Test
    public void testGetPersons() {
        // GIVEN
        when(personRepository.findAll()).thenReturn(persons);

        // WHEN
        List<Person> result = personService.getPersons();

        // THEN
        verify(personRepository).findAll();
        assertThat(result).isEqualTo(persons);
    }

    @Test
    public void testSavePerson() throws IOException {
        // GIVEN
        Person person = persons.getFirst();
        when(personRepository.save(person)).thenReturn(person);

        // WHEN
        Person result = personService.savePerson(person);

        // THEN
        verify(personRepository).save(person);
        assertThat(result).isEqualTo(person);
    }

    @Test
    public void testUpdatePerson() throws IOException {
        // GIVEN
        Person person = persons.getFirst();
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        when(personRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(person);
        when(personRepository.update(person)).thenReturn(person);

        // WHEN
        Person result = personService.updatePerson(person);

        // THEN
        verify(personRepository).update(person);
        assertThat(result).isEqualTo(person);
    }

    @Test
    public void testDeletePerson() throws IOException {
        // GIVEN
        Person person = persons.getFirst();
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        when(personRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(person);

        // WHEN
        personService.deletePerson(firstName, lastName);

        // THEN
        verify(personRepository).delete(person);
    }

    @Test
    public void testGetPersonInfoFromLastName() {
        // GIVEN
        when(personRepository.findByLastName("Smith")).thenReturn(persons);
        when(medicalRecordService.getByLastName("Smith")).thenReturn(medicalRecords);

        // WHEN
        List<PersonWithMedicalRecordDTO> personsWithMedicalRecord = personService.getPersonInfoFromLastName("Smith");

        // THEN
        verify(personRepository).findByLastName("Smith");
        verify(medicalRecordService).getByLastName("Smith");
        assertThat(personsWithMedicalRecord).hasSize(1);
    }

    @Test
    public void testGetChildrenFromAddress() {
        // GIVEN
        when(personRepository.findByAddress("123 Main St")).thenReturn(persons);
        when(medicalRecordService.getByLastNameIn(List.of("Smith"))).thenReturn(medicalRecords);

        // WHEN
        List<ChildDTO> children = personService.getChildrenFromAddress("123 Main St");

        // THEN
        verify(personRepository).findByAddress("123 Main St");
        verify(medicalRecordService).getByLastNameIn(List.of("Smith"));
        assertThat(children).hasSize(1);
    }

    @Test
    public void testGetCommunityEmailsByCity() {
        // GIVEN
        when(personRepository.findByCity("San Francisco")).thenReturn(persons);

        // WHEN
        List<String> emails = personService.getCommunityEmailsByCity("San Francisco");

        // THEN
        verify(personRepository).findByCity("San Francisco");
        assertThat(emails).hasSize(1);
    }

    @Test
    public void testGetByAddress() {
        // GIVEN
        when(personRepository.findByAddress("123 Main St")).thenReturn(persons);

        // WHEN
        List<Person> personsByAddress = personService.getByAddress("123 Main St");

        // THEN
        verify(personRepository).findByAddress("123 Main St");
        assertThat(personsByAddress).hasSize(1);
    }

    @Test
    public void testGetByAddressIn() {
        // GIVEN
        when(personRepository.findByAddressIn(List.of("123 Main St"))).thenReturn(persons);

        // WHEN
        List<Person> personsByAddress = personService.getByAddressIn(List.of("123 Main St"));

        // THEN
        verify(personRepository).findByAddressIn(List.of("123 Main St"));
        assertThat(personsByAddress).hasSize(1);
    }

}
