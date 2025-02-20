package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.AlertJson;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;

@Component
public class DataParser implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Override
    public void run(String... args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        AlertJson map = mapper.readValue(new File("src/main/resources/data.json"), AlertJson.class);

        parsePersons(map.getPersons());
        parseFireStations(map.getFirestations());
        parseMedicalRecords(map.getMedicalrecords());

    }

    private void parsePersons(List<Person> persons) {
        persons.forEach(personService::savePerson);
    }

    private void parseFireStations(List<FireStation> fireStations) {
        fireStations.forEach(fireStationService::saveFireStation);
    }

    private void parseMedicalRecords(List<MedicalRecord> medicalRecords) {
        medicalRecords.forEach(medicalRecordService::saveMedicalRecord);
    }

}
