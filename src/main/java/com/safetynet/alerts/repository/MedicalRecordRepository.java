package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository {
    Collection<MedicalRecord> findAll();

    MedicalRecord save(MedicalRecord medicalRecord) throws IOException;
    MedicalRecord update(MedicalRecord medicalRecord) throws IOException;
    void delete(Optional<MedicalRecord> medicalRecord) throws IOException;

    Collection<MedicalRecord> findByLastName(String lastName);
    Collection<MedicalRecord> findByLastNameIn(Collection<String> lastNames);
    Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName);
    Collection<MedicalRecord> findByLastNameInAndFirstNameIn(Collection<String> lastNames, Collection<String> firstNames);
}
