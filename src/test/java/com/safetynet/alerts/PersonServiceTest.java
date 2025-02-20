package com.safetynet.alerts;


import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    public void testSavePerson() {
        HashMap<String, String> input = new HashMap<>();
        input.put("firstName", "John");
        input.put("lastName", "Smith");
        input.put("address", "123 Main St.");
        input.put("city", "New York");
        input.put("zip", "44444");
        input.put("phone", "1213131313");
        input.put("email", "john.smith@gmail.com");

        Person person = personService.savePerson(input);

        assertThat(person).isNotNull();
        assertThat(person.getFirstName()).isEqualTo("John");
    }

}
