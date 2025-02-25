package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonInfosDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @PutMapping("/person")
    public Person updatePerson(@RequestBody Person input) {
        return personService.updatePerson(input);
    }

    @DeleteMapping("/person")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        personService.deletePerson(firstName, lastName);
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
