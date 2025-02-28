package com.safetynet.alerts.model;

import lombok.Data;

import java.util.Collection;

@Data
public class MedicalRecord {

    private String firstName;

    private String lastName;

    private String birthdate;

    private Collection<String> medications;

    private Collection<String> allergies;

}
