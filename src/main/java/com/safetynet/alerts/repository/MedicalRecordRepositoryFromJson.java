package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.DataParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MedicalRecordRepositoryFromJson implements MedicalRecordRepository {

    private final DataParser dataParser;

    public Collection<MedicalRecord> findAll() {
        return dataParser.getMedicalrecords();
    }

    public Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName) {
        return findAll()
                .stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .findFirst();
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        dataParser.getMedicalrecords().add(medicalRecord);
        return medicalRecord;
    }

    public MedicalRecord update(MedicalRecord medicalRecord) {
        dataParser.getMedicalrecords()
                .stream()
                .filter(recordToUpdate -> recordToUpdate.getFirstName().equals(medicalRecord.getFirstName()) && recordToUpdate.getLastName().equals(medicalRecord.getLastName()))
                .forEach(recordToUpdate -> {
                    recordToUpdate.setBirthdate(medicalRecord.getBirthdate());
                    recordToUpdate.setMedications(medicalRecord.getMedications());
                    recordToUpdate.setAllergies(medicalRecord.getAllergies());
                });
        return medicalRecord;
    }

    public void delete(Optional<MedicalRecord> medicalRecord) {
        boolean removed = dataParser.getMedicalrecords().remove(medicalRecord.get());
        System.out.println(removed);
    }
}
