package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ResidentDTO;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private DataMapper dataMapper;

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

    public Map<String, Object> getFireStationResidents(String address) {
        FireStation fireStation = fireStationRepository.findFirstByAddress(address);
        List<Person> persons = personRepository.findByAddress(address);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByLastNameIn(persons.stream().map(Person::getLastName).toList());

        return dataMapper.personWithMedicalRecordAndFireStationNumber(persons, medicalRecords, fireStation.getStation());
    }

    public Map<String, List<ResidentDTO>> getHousesByFireStations(List<String> stations) {
        List<FireStation> fireStations = fireStationRepository.getFireStationsByStationIn(stations);
        List<Person> persons = personRepository.findByAddressIn(fireStations.stream().map(FireStation::getAddress).toList());
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByLastNameIn(persons.stream().map(Person::getLastName).toList());
        return dataMapper.housesByFireStations(persons, medicalRecords, fireStations);
    }

    public Map<String, Object> getResidentsByFireStation(String stationNumber) {
        List<FireStation> fireStations = fireStationRepository.findByStation(stationNumber);
        List<Person> persons = personRepository.findByAddressIn(fireStations.stream().map(FireStation::getAddress).toList());
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByLastNameInAndFirstNameIn(
                persons.stream().map(Person::getLastName).toList(),
                persons.stream().map(Person::getFirstName).toList());

        return dataMapper.residentsByFireStation(persons, medicalRecords);
    }
}
