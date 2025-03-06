package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository {
    List<MedicalRecord> findAll();

    MedicalRecord save(MedicalRecord medicalRecord) throws IOException;
    MedicalRecord update(MedicalRecord medicalRecord) throws IOException;
    void delete(Optional<MedicalRecord> medicalRecord) throws IOException;

    List<MedicalRecord> findByLastName(String lastName);
    List<MedicalRecord> findByLastNameIn(List<String> lastNames);
    Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName);
    List<MedicalRecord> findByLastNameInAndFirstNameIn(List<String> lastNames, List<String> firstNames);
}
