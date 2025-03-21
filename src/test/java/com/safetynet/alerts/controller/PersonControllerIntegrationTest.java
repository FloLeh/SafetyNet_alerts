package com.safetynet.alerts.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.DataParserMock;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    DataParserMock dataParser;

    @BeforeEach
    public void tearDown() {
        dataParser.reset();
    }

    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(dataParser.getPersons().size())));
    }

    @Test
    public void testAddPerson() throws Exception {
        Person person = new Person("John", "Doe", "123 Main St", "San Francisco", "94105", "555-555-5555", "john@doe.com");

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.address", is("123 Main St")))
                .andExpect(jsonPath("$.city", is("San Francisco")))
                .andExpect(jsonPath("$.zip", is("94105")))
                .andExpect(jsonPath("$.phone", is("555-555-5555")))
                .andExpect(jsonPath("$.email", is("john@doe.com")));
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person person = new Person("John", "Boyd", "123 Main St", "San Francisco", "94105", "555-555-5555", "john@boyd.com");

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Boyd")))
                .andExpect(jsonPath("$.address", is("123 Main St")))
                .andExpect(jsonPath("$.city", is("San Francisco")))
                .andExpect(jsonPath("$.zip", is("94105")))
                .andExpect(jsonPath("$.phone", is("555-555-5555")))
                .andExpect(jsonPath("$.email", is("john@boyd.com")));
    }

    @Test
    public void testDeletePerson() throws Exception {
        mockMvc.perform(delete("/person").param("firstName", "John").param("lastName", "Boyd"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonInfo() throws Exception {
        String lastName = "Boyd";
        List<MedicalRecord> personMedicalRecords = dataParser.getMedicalrecords().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .toList();

        mockMvc.perform(get("/personInfo").param("lastName", lastName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(personMedicalRecords.size())))
                .andExpect(jsonPath("$[0].lastName", is(lastName)));
    }

    @Test
    public void testGetChildAlert() throws Exception {
        List<Person> persons = dataParser.getPersons().stream().filter(person -> person.getAddress().equals("1509 Culver St")).toList();
        List<String> lastNames = persons.stream().map(Person::getLastName).toList();
        List<String> firstNames = persons.stream().map(Person::getFirstName).toList();
        List<MedicalRecord> children = dataParser.getMedicalrecords().stream()
                .filter(medicalRecord -> lastNames.contains(medicalRecord.getLastName()))
                .filter(medicalRecord -> firstNames.contains(medicalRecord.getFirstName()))
                .filter(MedicalRecord::isMinor)
                .toList();

        mockMvc.perform(get("/childAlert").param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(children.size())))
                .andExpect(jsonPath("$[0].age", is(lessThanOrEqualTo(18))));
    }

    @Test
    public void testCommunityEmail() throws Exception {
        List<String> emails = dataParser.getPersons().stream().filter(person -> person.getCity().equals("Culver")).map(Person::getEmail).toList();

        mockMvc.perform(get("/communityEmail").param("city", "Culver"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(emails.size())));
    }
}
