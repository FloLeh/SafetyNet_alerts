package com.safetynet.alerts.model;

import lombok.Data;

import java.util.Collection;

@Data
public class AlertJson {

    public Collection<Person> persons;

    public Collection<Firestation> firestations;

    public Collection<MedicalRecord> medicalrecords;

}
