package com.safetynet.alerts.controller;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    @GetMapping("/medicalrecords")
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }

    @PostMapping("/medicalRecord")
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws IOException {
        return medicalRecordService.saveMedicalRecord(medicalRecord);
    }

    @PutMapping("/medicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws IOException {
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }

}
