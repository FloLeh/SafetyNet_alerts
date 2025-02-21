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

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setFirstName(input.getFirstName());
        medicalRecord.setLastName(input.getLastName());
        medicalRecord.setBirthdate(input.getBirthdate());
        medicalRecord.setMedications(input.getMedications());
        medicalRecord.setAllergies(input.getAllergies());

        return medicalRecordRepository.save(medicalRecord);
    }

    public Iterable<MedicalRecord> getByLastName(String lastName) {
        return medicalRecordRepository.findByLastName(lastName);
    }
}
