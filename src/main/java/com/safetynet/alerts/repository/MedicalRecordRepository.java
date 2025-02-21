package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {
    Iterable<MedicalRecord> findByLastName(String lastName);
    Iterable<MedicalRecord> findByLastNameIn(List<String> lastNames);
}
