package com.safetynet.alerts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "medicalRecord")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String birthdate;

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;

}
