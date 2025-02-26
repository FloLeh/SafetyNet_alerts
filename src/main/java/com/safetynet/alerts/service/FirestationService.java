package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;
    private final DataMapper dataMapper;

    public List<Firestation> getAllFirestations() {
        return firestationRepository.findAll();
    }

    public Firestation saveFirestation(final Firestation input) throws IOException {
        return firestationRepository.save(input);
    }

    public Firestation updateFirestation(final Firestation input) throws IOException {
        Assert.notNull(input.getStation(), "station is required");
        Assert.hasText(input.getAddress(), "address is required");

        Firestation fireStation = firestationRepository.findByAddress(input.getAddress())
                .orElseThrow(() -> new IllegalArgumentException("Invalid address"));

         fireStation.setStation(input.getStation());
         return firestationRepository.update(fireStation);
    }

    public void deleteFirestation(final Optional<String> address, final Optional<Integer> stationNumber, final String deleteBy) throws IOException {
        Assert.hasText(deleteBy, "deleteBy is required");
        if (deleteBy.equals("address")) {
            Assert.hasText(address.get(), "address is required");
            final Optional<Firestation> firestation = firestationRepository.findByAddress(address.get());
            firestationRepository.delete(firestation);
        } else if (deleteBy.equals("stationNumber")) {
            Assert.notNull(stationNumber.get(), "station is required");
            final List<Firestation> firestations = firestationRepository.findAll()
                    .stream()
                    .filter(firestation -> firestation.getStation().equals(stationNumber.get()))
                    .toList();
            firestationRepository.deleteAll(firestations);
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
