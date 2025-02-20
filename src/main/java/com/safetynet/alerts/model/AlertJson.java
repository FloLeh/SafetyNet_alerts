package com.safetynet.alerts.model;

import lombok.Data;

import java.util.List;

@Data
public class AlertJson {

    public List<Person> persons;

    public List<FireStation> firestations;

    public List<MedicalRecord> medicalrecords;

}
