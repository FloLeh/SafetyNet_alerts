package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.AlertJson;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Component
public class DataParser {

    private final AlertJson json;
    private final File file = new File("src/main/resources/data.json");
    private final ObjectMapper mapper;

    public DataParser (ObjectMapper mapper) throws IOException {
        this.mapper = mapper;
        this.json = mapper.readValue(file, AlertJson.class);
    }

    public Collection<Person> getPersons(){
        return json.getPersons();
    }

    public Collection<Firestation> getFirestations(){
        return json.getFirestations();
    }

    public Collection<MedicalRecord> getMedicalrecords(){
        return json.getMedicalrecords();
    }

    public void saveIntoJsonFile() throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, json);
    }

}
