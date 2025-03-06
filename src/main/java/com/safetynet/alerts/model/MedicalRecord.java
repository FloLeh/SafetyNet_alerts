package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
public class MedicalRecord {

    private String firstName;

    private String lastName;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate birthdate;

    private List<String> medications;

    private List<String> allergies;

    public Integer getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    public boolean isMinor() {
        return !isMajor();
    }

    public boolean isMajor() {
        return getAge() > 18;
    }

}
