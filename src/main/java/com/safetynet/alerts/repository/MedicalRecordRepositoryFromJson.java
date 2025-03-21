package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.DataParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MedicalRecordRepositoryFromJson implements MedicalRecordRepository {

    private final DataParser dataParser;

    public List<MedicalRecord> findAll() {
        return dataParser.getMedicalrecords();
    }

    public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {
        return findAll()
                .stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow();
    }

    public List<MedicalRecord> findByLastName(String lastName) {
        return dataParser.getMedicalrecords()
                .stream()
                .filter(medicalRecord -> medicalRecord.getLastName().equals(lastName))
                .toList();
    }

    public List<MedicalRecord> findByLastNameIn(List<String> lastNames) {
        return dataParser.getMedicalrecords()
                .stream()
                .filter(medicalRecord -> lastNames.contains(medicalRecord.getLastName()))
                .toList();
    }

    public List<MedicalRecord> findByLastNameInAndFirstNameIn(List<String> lastNames, List<String> firstNames) {
        return dataParser.getMedicalrecords()
                .stream()
                .filter(medicalRecord -> lastNames.contains(medicalRecord.getLastName()) && firstNames.contains(medicalRecord.getFirstName()))
                .toList();
    }

    public MedicalRecord save(MedicalRecord medicalRecord) throws IOException {
        dataParser.getMedicalrecords().add(medicalRecord);
        dataParser.save();
        return medicalRecord;
    }

    public MedicalRecord update(MedicalRecord medicalRecord) throws IOException {
        dataParser.getMedicalrecords()
                .stream()
                .filter(recordToUpdate -> recordToUpdate.getFirstName().equals(medicalRecord.getFirstName()) && recordToUpdate.getLastName().equals(medicalRecord.getLastName()))
                .forEach(recordToUpdate -> {
                    recordToUpdate.setBirthdate(medicalRecord.getBirthdate());
                    recordToUpdate.setMedications(medicalRecord.getMedications());
                    recordToUpdate.setAllergies(medicalRecord.getAllergies());
                });
        dataParser.save();
        return medicalRecord;
    }

    public void delete(MedicalRecord medicalRecord) throws IOException {
        dataParser.getMedicalrecords().remove(medicalRecord);
        dataParser.save();
    }

}
