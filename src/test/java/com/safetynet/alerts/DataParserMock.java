package com.safetynet.alerts;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataParser;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Primary
@Component
public class DataParserMock implements DataParser {

    List<Person> persons = new ArrayList<>();
    List<Firestation> firestations = new ArrayList<>();
    List<MedicalRecord> medicalRecords = new ArrayList<>();

    public DataParserMock(){
        reset();
    }

    @Override
    public List<Person> getPersons(){
        return persons;
    }

    @Override
    public List<Firestation> getFirestations(){
        return firestations;
    }

    @Override
    public List<MedicalRecord> getMedicalrecords(){
        return medicalRecords;
    }

    @Override
    public void save() {
    }

    public void reset(){
        persons.clear();
        firestations.clear();
        medicalRecords.clear();


        persons.addAll(
                List.of(
                        new Person("John", "Boyd", "1509 Culver St", "Culver","97451","841-874-6512","jaboyd@email.com"),
                        new Person("Jacob", "Boyd", "1509 Culver St", "Culver","97451","841-874-6511","jacob@email.com"),
                        new Person("Tenley", "Boyd", "1509 Culver St", "Culver","97451","841-874-6513","tenley@email.com"),
                        new Person("Roger", "Boyd", "1509 Culver St", "Culver","97451","841-874-6514","roger@email.com"),
                        new Person("Jonanathan", "Marrack", "29 15th St", "Culver","97451","841-874-6523","jona@email.com"),
                        new Person("Tessa", "Carman", "834 Binoc Ave", "Culver","97451","841-874-6534","tessa@email.com")
                )
        );
        firestations.addAll(
                List.of(
                        new Firestation("1509 Culver St",3),
                        new Firestation("29 15th St",2),
                        new Firestation("834 Binoc Ave",3)
                )
        );

        medicalRecords.addAll(
                List.of(
                        new MedicalRecord("John","Boyd", LocalDate.of(1984, 6, 3),List.of("aznol:350mg", "hydrapermazol:100mg"), List.of( "nillacilan")),
                        new MedicalRecord("Jacob","Boyd", LocalDate.of(1989, 6, 3),List.of("pharmacol:5000mg","terazine:10mg","noznazol:250mg"), List.of()),
                        new MedicalRecord("Tenley","Boyd", LocalDate.of(2012, 6, 3),List.of(), List.of("peanut")),
                        new MedicalRecord("Roger","Boyd", LocalDate.of(2017, 6, 3),List.of(), List.of("")),
                        new MedicalRecord("Jonanathan","Marrack", LocalDate.of(1989, 6, 3),List.of(), List.of("")),
                        new MedicalRecord("Tessa","Carman", LocalDate.of(2012, 6, 3),List.of(), List.of(""))
                ));

    }

}
