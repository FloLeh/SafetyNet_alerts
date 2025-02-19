package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;

@Component
public class DataParser implements CommandLineRunner {

    @Autowired
    PersonService personService;

    @Override
    public void run(String... args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, List<HashMap<String, String>>> map = mapper.readValue(new File("src/main/resources/data.json"), HashMap.class);

        List<HashMap<String, String>> persons = map.get("persons");

        persons.forEach(personService::savePerson);

    }
}
