package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord saveMedicalRecord(final MedicalRecord input) throws IOException {
        return medicalRecordRepository.save(input);
    }

    public MedicalRecord updateMedicalRecord(final MedicalRecord input) throws IOException {
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(input.getFirstName(), input.getLastName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid firstName or lastName"));

        if (input.getBirthdate() != null) {
            medicalRecord.setBirthdate(input.getBirthdate());
        }
        if (input.getMedications() != null) {
            medicalRecord.setMedications(input.getMedications());
        }
        if (input.getAllergies() != null) {
            medicalRecord.setAllergies(input.getAllergies());
        }

        return medicalRecordRepository.update(medicalRecord);
    }

    public void deleteMedicalRecord(final String firstName, final String lastName) throws IOException {
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
        medicalRecordRepository.delete(medicalRecord);
    }

    public Collection<MedicalRecord> getMedicalRecordByLastName(final String lastName) {
        return medicalRecordRepository.findByLastName(lastName);
    }

    public Collection<MedicalRecord> getByLastNameIn(Collection<String> lastNames) {
        return medicalRecordRepository.findByLastNameIn(lastNames);
    }

    public Collection<MedicalRecord> getByLastNameInAndFirstNameIn(Collection<String> lastNames, Collection<String> firstNames) {
        return medicalRecordRepository.findByLastNameInAndFirstNameIn(lastNames, firstNames);
    }
}
