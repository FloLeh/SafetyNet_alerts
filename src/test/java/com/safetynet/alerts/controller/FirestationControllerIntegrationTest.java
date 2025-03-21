package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.DataParserMock;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerIntegrationTest  {

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
    public void testGetAllFirestations() throws Exception {
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(dataParser.getFirestations().size())));
    }

    @Test
    public void testAddFirestation() throws Exception {
        Firestation firestation = new Firestation();
        firestation.setAddress("123 Main St");
        firestation.setStation(1);

        mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(firestation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address", is("123 Main St")))
                .andExpect(jsonPath("$.station", is(1)));
    }

    @Test
    public void testUpdateFirestation() throws Exception {
        Firestation firestation = new Firestation();
        firestation.setAddress("29 15th St");
        firestation.setStation(4);

        mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(firestation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station", is(4)));
    }

    @Test
    public void testDeleteFirestation() throws Exception {
        mockMvc.perform(delete("/firestation").param("address", "1509 Culver St"))
                .andExpect(status().isOk());
    }


    @Test
    public void testGetResidentsByStation() throws Exception {
        List<String> firestationAddresses = dataParser.getFirestations().stream()
                .filter(firestation -> firestation.getStation().equals(3))
                .map(Firestation::getAddress)
                .toList();
        int residentsNumber = dataParser.getPersons().stream()
                .filter(person -> firestationAddresses.contains(person.getAddress()))
                .toList()
                .size();

        mockMvc.perform(get("/firestation").param("stationNumber", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents", hasSize(residentsNumber)));
    }

    @Test
    public void testGetPhoneAlert() throws Exception {
        List<String> firestationAddresses = dataParser.getFirestations().stream()
                .filter(firestation -> firestation.getStation().equals(3))
                .map(Firestation::getAddress)
                .toList();
        int phoneNumbers = dataParser.getPersons().stream()
                .filter(person -> firestationAddresses.contains(person.getAddress()))
                .map(Person::getPhone)
                .distinct()
                .toList()
                .size();

        mockMvc.perform(get("/phoneAlert").param("firestation", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(phoneNumbers)));
    }

    @Test
    public void testGetFirestationResidents() throws Exception {
        int residentsNumber = dataParser.getPersons().stream()
                .filter(person -> person.getAddress().equals("1509 Culver St"))
                .toList()
                .size();

        mockMvc.perform(get("/fire").param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents", hasSize(residentsNumber)));
    }

    @Test
    public void testGetHousesByFirestations() throws Exception {
        int result = dataParser.getPersons().stream()
                .filter(person -> person.getAddress().equals("1509 Culver St"))
                .toList()
                .size();

        mockMvc.perform(get("/flood/stations").param("stations", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['1509 Culver St']", hasSize(result)));
    }
}
