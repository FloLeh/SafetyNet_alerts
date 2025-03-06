package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FirestationResidentWithMedicalRecordDTO;
import com.safetynet.alerts.dto.FirestationResidentsByAddressDTO;
import com.safetynet.alerts.dto.FirestationResidentsCountDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.io.IOException;
import java.util.List;

public interface FirestationService {
    List<Firestation> getAllFirestations();

    Firestation saveFirestation(final Firestation input) throws IOException;
    Firestation updateFirestation(final Firestation input) throws IOException;
    void deleteFirestation(final String address, final Integer stationNumber) throws IOException;

    List<String> getPhoneNumbersByFirestationNumber(int station);
    FirestationResidentsByAddressDTO getFirestationResidents(String address);
    FirestationResidentsCountDTO getResidentsByFirestation(Integer stationNumber);
    List<FirestationResidentWithMedicalRecordDTO> residentsByMedicalRecord(List<Person> persons, List<MedicalRecord> medicalRecords);
    FirestationResidentsByAddressDTO personWithMedicalRecordAndFirestationNumber(List<Person> persons, List<MedicalRecord> medicalRecords, int station);
    FirestationResidentsCountDTO residentsByFirestation(List<Person> persons, List<MedicalRecord> medicalRecords);
}