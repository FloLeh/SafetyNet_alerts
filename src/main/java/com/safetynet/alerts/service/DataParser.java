package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.AlertJson;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class DataParser {

    private final AlertJson json;
    private final File file = new ClassPathResource("data.json").getFile();
    private final ObjectMapper mapper;

    public DataParser (ObjectMapper mapper) throws IOException {
        this.mapper = mapper;
        this.json = mapper.readValue(file, AlertJson.class);
    }

    public List<Person> getPersons(){
        return json.getPersons();
    }

    public List<Firestation> getFirestations(){
        return json.getFirestations();
    }

    public List<MedicalRecord> getMedicalrecords(){
        return json.getMedicalrecords();
    }

    public void saveIntoJsonFile() throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, json);
    }

}
