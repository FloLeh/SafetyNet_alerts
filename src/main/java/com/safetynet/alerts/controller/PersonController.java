package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonInfosDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return personService.getPersons();
    }

    @GetMapping("/personInfolastName={lastName}")
    public List<PersonInfosDTO> getPersonInfoLastName(@PathVariable String lastName) {
        return personService.getPersonInfoFromLastName(lastName);
    }

    @GetMapping("/childAlert")
    public List<ChildDTO> getChildAlert(@RequestParam String address) {
        return personService.getChildrenFromAddress(address);
    }

    @GetMapping("/communityEmail")
    public List<String> getCommunityEmail(@RequestParam String city) {
        return personService.getCommunityEmailsByCity(city);
    }

}
