package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MedicalRecordController {

    final MedicalRecordServiceImpl medicalRecordService;

    /**
     * Read - Get all the medical records
     * @return A list of medical record
     */
    @GetMapping("/medicalRecords")
    public List<MedicalRecord> getMedicalRecords() {
        log.info("GET: /medicalRecords");
        return medicalRecordService.getMedicalRecords();
    }

    /**
     * Create - Create a new medical record
     * @param medicalRecord A medical record
     * @return The new medical record
     */
    @PostMapping("/medicalRecord")
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws IOException {
        log.info("POST: /medicalRecord");
        return medicalRecordService.saveMedicalRecord(medicalRecord);
    }

    /**
     * Update - Save medical record
     * @param medicalRecord A medical record
     * @return The updated medical record
     */
    @PutMapping("/medicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws IOException {
        log.info("PUT: /medicalRecord");
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    /**
     * Delete - Delete a medical record
     * @param firstName A first name to identify
     * @param lastName A last name to identify
     */
    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        log.info("DELETE: /medicalRecord");
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }

}
