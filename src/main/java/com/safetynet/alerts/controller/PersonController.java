package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.PersonWithMedicalRecordDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PersonController {

    private final PersonServiceImpl personService;

    /**
     * Read - Get all the persons
     * @return A list of persons
     */
    @GetMapping("/persons")
    public List<Person> getPersons() {
        return personService.getPersons();
    }

    /**
     * Create - Create a new person
     * @param person A person
     * @return The new person
     */
    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) throws IOException {
        return personService.savePerson(person);
    }

    /**
     * Update - Save a person
     * @param person A person
     * @return The updated person
     */
    @PutMapping("/person")
    public Person updatePerson(@RequestBody Person person) throws IOException {
        return personService.updatePerson(person);
    }

    /**
     * Delete - Delete a person
     * @param firstName A first name to identify
     * @param lastName A last name to identify
     */
    @DeleteMapping("/person")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) throws IOException {
        personService.deletePerson(firstName, lastName);
    }

    /**
     * Read - Get persons of the same last name with their medical records
     * @param lastName A last name to identify
     * @return A list of persons
     */
    @GetMapping("/personInfo")
    public List<PersonWithMedicalRecordDTO> getPersonInfoLastName(@RequestParam String lastName) {
        return personService.getPersonInfoFromLastName(lastName);
    }

    /**
     * Read - Get children by address
     * @param address An address
     * @return A list of children
     */
    @GetMapping("/childAlert")
    public List<ChildDTO> getChildAlert(@RequestParam String address) {
        return personService.getChildrenFromAddress(address);
    }

    /**
     * Read - Get all the emails for a city
     * @param city A City
     * @return A list of emails
     */
    @GetMapping("/communityEmail")
    public List<String> getCommunityEmail(@RequestParam String city) {
        return personService.getCommunityEmailsByCity(city);
    }

}
