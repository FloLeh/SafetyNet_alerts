package com.safetynet.alerts.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() throws Exception {
        this.cleanDatabase();
    }

    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void testAddPerson() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setCity("San Francisco");
        person.setZip("94105");
        person.setPhone("555-555-5555");
        person.setEmail("john@doe.com");

        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", Matchers.is("John")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Doe")))
                .andExpect(jsonPath("$.address", Matchers.is("123 Main St")));
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Boyd");
        person.setAddress("123 Main St");
        person.setCity("San Francisco");
        person.setZip("94105");
        person.setPhone("555-555-5555");
        person.setEmail("john@boyd.com");

        mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", Matchers.is("John")))
                .andExpect(jsonPath("$.lastName", Matchers.is("Boyd")))
                .andExpect(jsonPath("$.address", Matchers.is("123 Main St")));
    }

    @Test
    public void testDeletePerson() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("firstName", List.of("Jacob"));
        map.put("lastName", List.of("Boyd"));
        mockMvc.perform(delete("/person").params(map))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonInfo() throws Exception {
        mockMvc.perform(get("/personInfo").param("lastName", "Boyd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].lastName", is("Boyd")));
    }

    @Test
    public void testGetChildAlert() throws Exception {
        mockMvc.perform(get("/childAlert").param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].age", is(lessThanOrEqualTo(18))));
    }

    @Test
    public void testCommunityEmail() throws Exception {
        mockMvc.perform(get("/communityEmail").param("city", "Culver"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }
}
