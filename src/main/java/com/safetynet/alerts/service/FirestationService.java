package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;
    private final DataMapper dataMapper;

    public List<FireStation> getAllFireStations() {
        return (List<FireStation>) fireStationRepository.findAll();
    }

    public FireStation saveFireStation(FireStation input) {
        return fireStationRepository.save(input);
    }

    public FireStation updateFireStation(FireStation input) {
        FireStation fireStation = fireStationRepository.findFirstByAddress(input.getAddress());
        if (fireStation != null) {
            if (input.getStation() != null) {
                fireStation.setStation(input.getStation());
            }
            return fireStationRepository.save(fireStation);
        }
        return null;
    }

    public void deleteFireStation(FireStation input) {
        String address = input.getAddress();
        String station = input.getStation();
        if (address != null) {
            FireStation fireStation = fireStationRepository.findFirstByAddress(address);
            if (fireStation != null) {
                fireStationRepository.delete(fireStation);
            }
        }
        if (station != null) {
            List<FireStation> fireStations = fireStationRepository.findByStation(station);
            if (!fireStations.isEmpty()) {
                fireStationRepository.deleteAll(fireStations);
            }
        }
    }

//    public List<String> getPhoneNumbersByFirestationNumber(int station) {
//        List<Firestation> fireStations = firestationRepository.findByStationNumber(station);
//
//        List<Person> persons = personRepository.findByAddressIn(fireStations.stream().map(Firestation::getAddress).toList());
//
//        List<String> phoneNumbers = new ArrayList<>();
//        for (Person person : persons) {
//            phoneNumbers.add(person.getPhone());
//        }
//        return phoneNumbers.stream().distinct().toList();
//    }
//
//    public Map<String, Object> getFirestationResidents(String address) {
//        Firestation fireStation = firestationRepository.findByAddress(address)
//                .orElseThrow();
//
//        List<Person> persons = personRepository.findByAddress(address);
//        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByLastNameIn(persons.stream().map(Person::getLastName).toList());
//
//        return dataMapper.personWithMedicalRecordAndFirestationNumber(persons, medicalRecords, fireStation.getStation());
//    }
//
//    public Map<String, List<ResidentDTO>> getHousesByFirestations(List<Integer > stations) {
//        List<Firestation> fireStations = firestationRepository.getFirestationsByStationIn(stations);
//        List<Person> persons = personRepository.findByAddressIn(fireStations.stream().map(Firestation::getAddress).toList());
//        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByLastNameIn(persons.stream().map(Person::getLastName).toList());
//        return dataMapper.housesByFirestations(persons, medicalRecords, fireStations);
//    }
//
//    public Map<String, Object> getResidentsByFirestation(Integer stationNumber) {
//        List<Firestation> fireStations = firestationRepository.findBystationNumber(stationNumber);
//        List<Person> persons = personRepository.findByAddressIn(fireStations.stream().map(Firestation::getAddress).toList());
//        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByLastNameInAndFirstNameIn(
//                persons.stream().map(Person::getLastName).toList(),
//                persons.stream().map(Person::getFirstName).toList());
//
//        return dataMapper.residentsByFirestation(persons, medicalRecords);
//    }

}
