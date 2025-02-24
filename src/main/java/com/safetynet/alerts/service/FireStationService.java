package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private PersonRepository personRepository;

    public List<FireStation> getAllFireStations() {
        return (List<FireStation>) fireStationRepository.findAll();
    }

    public FireStation saveFireStation(FireStation input) {
        FireStation fireStation = new FireStation();

        fireStation.setAddress(input.getAddress());
        fireStation.setStation(input.getStation());

        return fireStationRepository.save(fireStation);
    }

    public List<String> getPhoneNumbersByFireStation(String station) {
        List<FireStation> fireStations = fireStationRepository.findByStation(station);

        List<Person> persons = personRepository.findByAddressIn(fireStations.stream().map(FireStation::getAddress).toList());

        List<String> phoneNumbers = new ArrayList<>();
        for (Person person : persons) {
            phoneNumbers.add(person.getPhone());
        }
        return phoneNumbers.stream().distinct().toList();
    }

}
