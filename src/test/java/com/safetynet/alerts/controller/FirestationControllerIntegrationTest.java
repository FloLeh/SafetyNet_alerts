package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Firestation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() throws Exception {
        this.cleanDatabase();
    }

    @Test
    public void testGetAllFirestations() throws Exception {
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
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
        firestation.setStation(2);

        mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(firestation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station", is(2)));
    }

    @Test
    public void testDeleteFirestation() throws Exception {
        mockMvc.perform(delete("/firestation").param("address", "834 Binoc Ave"))
                .andExpect(status().isOk());
    }


    @Test
    public void testGetResidentsByStation() throws Exception {
        mockMvc.perform(get("/firestation").param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents", hasSize(greaterThan(0))));
    }

    @Test
    public void testGetPhoneAlert() throws Exception {
        mockMvc.perform(get("/phoneAlert").param("firestation", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void testGetFirestationResidents() throws Exception {
        mockMvc.perform(get("/fire").param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents", hasSize(greaterThan(0))));
    }

    @Test
    public void testGetHousesByFirestations() throws Exception {
        mockMvc.perform(get("/flood/stations").param("stations", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['908 73rd St']", hasSize(greaterThan(0))));
    }
}
