package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.AlertJson;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Component
public class DataParser {

    private final AlertJson map;

    public DataParser (ObjectMapper mapper) throws IOException {
        this.map = mapper.readValue(new File("src/main/resources/data.json"), AlertJson.class);
    }

    public Collection<Person> getPersons(){
        return map.getPersons();
    }

    public Collection<Firestation> getFirestations(){
        return map.getFirestations();
    }

    public Collection<MedicalRecord> getMedicalrecords(){
        return map.getMedicalrecords();
    }

    //TODO(SAVE) pour update le JSON


}
