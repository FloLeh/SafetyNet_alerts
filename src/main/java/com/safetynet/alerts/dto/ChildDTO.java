package com.safetynet.alerts.dto;


import com.safetynet.alerts.model.Person;

import java.util.List;

public record ChildDTO(
        String firstName,
        String lastName,
        int age,
        List<Person> householdMembers
) {
}
