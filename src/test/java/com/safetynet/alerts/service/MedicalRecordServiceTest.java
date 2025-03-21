package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    static List<MedicalRecord> medicalRecords = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        MedicalRecord medicalRecord = new MedicalRecord("John", "Smith", LocalDate.of(2010, 1 ,1), List.of("Doliprane"), List.of("Peanuts"));
        medicalRecords.add(medicalRecord);
    }

    @Test
    void testGetMedicalRecords() {
        // GIVEN
        when(medicalRecordRepository.findAll()).thenReturn(medicalRecords);

        // WHEN
        List<MedicalRecord> result = medicalRecordService.getMedicalRecords();

        // THEN
        verify(medicalRecordRepository).findAll();
        assertThat(result).isEqualTo(medicalRecords);
    }

    @Test
    void testSaveMedicalRecord() throws IOException {
        // GIVEN
        MedicalRecord medicalRecord = medicalRecords.getFirst();
        when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);

        // WHEN
        MedicalRecord result = medicalRecordService.saveMedicalRecord(medicalRecord);

        // THEN
        verify(medicalRecordRepository).save(medicalRecord);
        assertThat(result).isEqualTo(medicalRecord);
    }

    @Test
    void testUpdateMedicalRecord() throws IOException {
        // GIVEN
        MedicalRecord medicalRecord = medicalRecords.getFirst();
        when(medicalRecordRepository.findByFirstNameAndLastName(any(), any())).thenReturn(medicalRecord);
        when(medicalRecordRepository.update(medicalRecord)).thenReturn(medicalRecord);

        // WHEN
        MedicalRecord result = medicalRecordService.updateMedicalRecord(medicalRecord);

        // THEN
        verify(medicalRecordRepository).update(medicalRecord);
        assertThat(result).isEqualTo(medicalRecord);
    }

    @Test
    void testDeleteMedicalRecord() throws IOException {
        // GIVEN
        MedicalRecord medicalRecord = medicalRecords.getFirst();
        String firstName = medicalRecord.getFirstName();
        String lastName = medicalRecord.getLastName();
        when(medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(medicalRecord);

        // WHEN
        medicalRecordService.deleteMedicalRecord(firstName, lastName);

        // THEN
        verify(medicalRecordRepository).delete(medicalRecord);
    }

    @Test
    void testGetMedicalRecordByLastName() {
        // GIVEN
        when(medicalRecordRepository.findByLastName("Smith")).thenReturn(medicalRecords);

        // WHEN
        List<MedicalRecord> result = medicalRecordService.getByLastName("Smith");

        // THEN
        verify(medicalRecordRepository).findByLastName("Smith");
        assertThat(result).isEqualTo(medicalRecords);
    }

    @Test
    void testGetByLastNameIn() {
        // GIVEN
        when(medicalRecordRepository.findByLastNameIn(List.of("Smith"))).thenReturn(medicalRecords);

        // WHEN
        List<MedicalRecord> result = medicalRecordService.getByLastNameIn(List.of("Smith"));

        // THEN
        verify(medicalRecordRepository).findByLastNameIn(List.of("Smith"));
        assertThat(result).isEqualTo(medicalRecords);
    }

    @Test
    void testGetByLastNameInAndFirstNameIn() {
        // GIVEN
        when(medicalRecordRepository.findByLastNameInAndFirstNameIn(List.of("Smith"), List.of("John"))).thenReturn(medicalRecords);
        // WHEN
        List<MedicalRecord> result = medicalRecordService.getByLastNameInAndFirstNameIn(List.of("Smith"), List.of("John"));

        // THEN
        verify(medicalRecordRepository).findByLastNameInAndFirstNameIn(List.of("Smith"), List.of("John"));
        assertThat(result).isEqualTo(medicalRecords);
    }

}
