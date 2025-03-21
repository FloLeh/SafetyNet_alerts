package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.io.IOException;
import java.util.List;

public interface DataParser {
    List<Person> getPersons();
    List<Firestation> getFirestations();
    List<MedicalRecord> getMedicalrecords();
    void save() throws IOException;
}
