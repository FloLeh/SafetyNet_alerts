package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;

@Component
public class DataParser implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Iterable<Person>> map = mapper.readValue(new File("src/main/resources/data.json"), HashMap.class);

        Iterable<Person> persons = map.get("persons");
        System.out.println(persons);

    }
}
