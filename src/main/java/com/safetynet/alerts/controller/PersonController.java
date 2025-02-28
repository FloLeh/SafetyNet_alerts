package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonInfosDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PersonController {

    private final PersonService personService;

    @GetMapping("/persons")
    public Collection<Person> getPersons() {
        return personService.getPersons();
    }

    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) throws IOException {
        return personService.savePerson(person);
    }

    @PutMapping("/person")
    public Person updatePerson(@RequestBody Person input) throws IOException {
        return personService.updatePerson(input);
    }

    @DeleteMapping("/person")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        personService.deletePerson(firstName, lastName);
    }

    @GetMapping("/personInfo")
    public Collection<PersonInfosDTO> getPersonInfoLastName(@RequestParam String lastName) {
        return personService.getPersonInfoFromLastName(lastName);
    }

    @GetMapping("/childAlert")
    public Collection<ChildDTO> getChildAlert(@RequestParam String address) {
        return personService.getChildrenFromAddress(address);
    }

    @GetMapping("/communityEmail")
    public Collection<String> getCommunityEmail(@RequestParam String city) {
        return personService.getCommunityEmailsByCity(city);
    }

}
