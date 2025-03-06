package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;

import java.io.IOException;
import java.util.List;

public interface MedicalRecordService {
    Iterable<MedicalRecord> getMedicalRecords();

    MedicalRecord saveMedicalRecord(final MedicalRecord input) throws IOException;
    MedicalRecord updateMedicalRecord(final MedicalRecord input) throws IOException;
    void deleteMedicalRecord(final String firstName, final String lastName) throws IOException;

    List<MedicalRecord> getMedicalRecordByLastName(final String lastName);
    List<MedicalRecord> getByLastNameIn(List<String> lastNames);
    List<MedicalRecord> getByLastNameInAndFirstNameIn(List<String> lastNames, List<String> firstNames);
}
