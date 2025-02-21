package com.safetynet.alerts;


import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    public void testSavePerson() {
        Person input = new Person();
        input.setFirstName("John");
        input.setLastName("Smith");
        input.setAddress("123 Main St.");
        input.setCity("New York");
        input.setZip("44444");
        input.setPhone("1213131313");
        input.setEmail("john.smith@gmail.com");

        Person person = personService.savePerson(input);

        assertThat(person).isNotNull();
        assertThat(person.getFirstName()).isEqualTo("John");
    }

}
