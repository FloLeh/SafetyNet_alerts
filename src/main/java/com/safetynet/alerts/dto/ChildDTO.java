package com.safetynet.alerts.dto;


import com.safetynet.alerts.model.Person;

import java.util.Collection;

public record ChildDTO(
        String firstName,
        String lastName,
        String age,
        Collection<Person> householdMembers
) {
}
