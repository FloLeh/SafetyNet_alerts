package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FirestationResidentWithMedicalRecordDTO;
import com.safetynet.alerts.dto.FirestationResidentsByAddressDTO;
import com.safetynet.alerts.dto.FirestationResidentsCountDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FirestationServiceTest {

    @Mock
    private FirestationRepository firestationRepository;

    @Mock
    private PersonServiceImpl personService;

    @Mock
    private MedicalRecordServiceImpl medicalRecordService;

    @InjectMocks
    private FirestationServiceImpl firestationService;

    static List<Person> persons = new ArrayList<>();
    static List<MedicalRecord> medicalRecords = new ArrayList<>();
    static List<Firestation> firestations = new ArrayList<>();
    static final String firstName = "John";
    static final String lastName = "Smith";
    static final String address = "123 Main St";

    @BeforeAll
    static void setUp() {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(address);
        person.setCity("San Francisco");
        person.setZip("94105");
        person.setPhone("555-555-5555");
        person.setEmail("john.smith@gmail.com");
        persons.add(person);

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName(firstName);
        medicalRecord.setLastName(lastName);
        medicalRecord.setBirthdate(LocalDate.of(2010, 1 ,1));
        medicalRecord.setMedications(List.of("Doliprane"));
        medicalRecord.setAllergies(List.of("Peanuts"));
        medicalRecords.add(medicalRecord);

        Firestation firestation = new Firestation();
        firestation.setStation(1);
        firestation.setAddress(address);
        firestations.add(firestation);
    }

    @Test
    public void testGetAllFirestations() {
        // GIVEN
        when(firestationRepository.findAll()).thenReturn(firestations);

        // WHEN
        List<Firestation> result = firestationService.getAllFirestations();

        // THEN
        verify(firestationRepository).findAll();
        assertThat(result).isEqualTo(firestations);
    }

    @Test
    public void testSaveFirestation() throws IOException {
        // GIVEN
        Firestation firestation = firestations.getFirst();
        when(firestationRepository.save(firestation)).thenReturn(firestation);

        // WHEN
        Firestation result = firestationService.saveFirestation(firestation);

        // THEN
        verify(firestationRepository).save(firestation);
        assertThat(result).isEqualTo(firestation);
    }

    @Test
    public void testUpdateFirestation() throws IOException {
        // GIVEN
        Firestation firestation = firestations.getFirst();
        when(firestationRepository.findByAddress(firestation.getAddress())).thenReturn(firestation);
        when(firestationRepository.update(firestation)).thenReturn(firestation);

        // WHEN
        Firestation result = firestationService.updateFirestation(firestation);

        // THEN
        verify(firestationRepository).update(firestation);
        assertThat(result).isEqualTo(firestation);
    }

    @Test
    public void testDeleteFirestation() throws IOException {
        // GIVEN
        Firestation firestation = firestations.getFirst();
        when(firestationRepository.findByAddress(firestation.getAddress())).thenReturn(firestation);
        when(firestationRepository.update(firestation)).thenReturn(firestation);

        // WHEN
        firestationService.deleteFirestation(firestation.getAddress(), null);

        // THEN
        verify(firestationRepository).delete(firestation);
    }

    @Test
    public void testDeleteAllFirestationsByStation() throws IOException {
        // GIVEN
        when(firestationRepository.findAll()).thenReturn(firestations);

        // WHEN
        firestationService.deleteFirestation(null, 1);

        // THEN
        verify(firestationRepository).deleteAll(firestations);
    }

    @Test
    public void testGetPhoneNumbersByFirestationNumber()  {
        // GIVEN
        when(firestationRepository.findByStationNumber(1)).thenReturn(firestations);
        when(personService.getByAddressIn(List.of(address))).thenReturn(persons);

        // WHEN
        List<String> result = firestationService.getPhoneNumbersByFirestationNumber(1);

        // THEN
        verify(firestationRepository).findByStationNumber(1);
        verify(personService).getByAddressIn(List.of(address));
        assertThat(result).hasSize(1);
    }

    @Test
    public void testGetFirestationResidents()  {
        // GIVEN
        Firestation firestation = firestations.getFirst();
        when(firestationRepository.findByAddress(address)).thenReturn(firestation);
        when(personService.getByAddress(address)).thenReturn(persons);
        when(medicalRecordService.getByLastNameIn(persons.stream().map(Person::getLastName).toList())).thenReturn(medicalRecords);

        // WHEN
        FirestationResidentsByAddressDTO result = firestationService.getFirestationResidents(address);

        // THEN
        verify(firestationRepository).findByAddress(address);
        verify(personService).getByAddress(address);
        assertThat(result.residents()).hasSize(1);
        assertThat(result.station()).isEqualTo(1);
    }

    @Test
    public void testGetHousesByFirestations()  {
        // GIVEN
        when(firestationRepository.getFirestationsByStationIn(List.of(1))).thenReturn(firestations);
        when(personService.getByAddressIn(List.of(address))).thenReturn(persons);
        when(medicalRecordService.getByLastNameIn(List.of(lastName))).thenReturn(medicalRecords);

        // WHEN
        Map<String, List<FirestationResidentWithMedicalRecordDTO>> result = firestationService.getHousesByFirestations(List.of(1));

        // THEN
        verify(firestationRepository).getFirestationsByStationIn(List.of(1));
        verify(personService).getByAddressIn(List.of(address));
        verify(medicalRecordService).getByLastNameIn(List.of(lastName));
        assertThat(result.keySet()).contains(address);
        assertThat(result.values()).hasSize(1);
    }
    @Test
    public void testGetResidentsByFirestation()  {
        // GIVEN
        when(firestationRepository.findByStationNumber(1)).thenReturn(firestations);
        when(personService.getByAddressIn(List.of(address))).thenReturn(persons);
        when(medicalRecordService.getByLastNameInAndFirstNameIn(List.of(lastName), List.of(firstName))).thenReturn(medicalRecords);

        // WHEN
        FirestationResidentsCountDTO result = firestationService.getResidentsByFirestation(1);

        // THEN
        verify(firestationRepository).findByStationNumber(1);
        verify(personService).getByAddressIn(List.of(address));
        verify(medicalRecordService).getByLastNameInAndFirstNameIn(List.of(lastName), List.of(firstName));
        assertThat(result.residents()).hasSize(1);
        assertThat(result.children()).isEqualTo(1);
        assertThat(result.adults()).isEqualTo(0);
    }

}
