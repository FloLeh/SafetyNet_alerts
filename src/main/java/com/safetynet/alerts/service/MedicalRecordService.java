package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord input) {
        return medicalRecordRepository.save(input);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord input) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(input.getFirstName(), input.getLastName());

        if (medicalRecord != null) {
            if (input.getBirthdate() != null) {
                medicalRecord.setBirthdate(input.getBirthdate());
            }
            if (input.getMedications() != null) {
                medicalRecord.setMedications(input.getMedications());
            }
            if (input.getAllergies() != null) {
                medicalRecord.setAllergies(input.getAllergies());
            }
            return medicalRecordRepository.save(medicalRecord);
        }
        return null;
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
        medicalRecordRepository.delete(medicalRecord);
    }
}
