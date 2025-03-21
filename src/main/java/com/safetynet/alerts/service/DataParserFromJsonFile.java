package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.AlertJson;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class DataParserFromJsonFile implements DataParser {

    private final static String RESOURCE_NAME = "data.json";

    private final AlertJson json;
    private final File file = new ClassPathResource(RESOURCE_NAME).getFile();
    private final ObjectMapper mapper;

    public DataParserFromJsonFile(final ObjectMapper mapper) throws IOException {
        this.mapper = mapper;
        this.json = mapper.readValue(file, AlertJson.class);
    }

    @Override
    public List<Person> getPersons(){
        return json.getPersons();
    }

    @Override
    public List<Firestation> getFirestations(){
        return json.getFirestations();
    }

    @Override
    public List<MedicalRecord> getMedicalrecords(){
        return json.getMedicalrecords();
    }

    @Override
    public void save() throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, json);
    }

}
