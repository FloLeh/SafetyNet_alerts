package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonInfosDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.service.DataMapper;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private DataMapper dataMapper;

    @GetMapping("/persons")
    public Iterable<Person> getAllPersons() {
        return personService.getPersons();
    }

    @GetMapping("/personInfolastName={lastName}")
    public List<PersonInfosDTO> getPersonInfoLastName(@PathVariable String lastName) {
        return personService.getPersonInfoFromLastName(lastName);
    }

}
