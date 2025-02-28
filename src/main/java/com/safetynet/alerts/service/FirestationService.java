package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ResidentDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class FirestationService {

    private final FirestationRepository firestationRepository;
    private final DataMapper dataMapper;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    public Collection<Firestation> getAllFirestations() {
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
            final Collection<Firestation> firestations = firestationRepository.findAll()
                    .stream()
                    .filter(firestation -> firestation.getStation().equals(stationNumber.get()))
                    .toList();
            firestationRepository.deleteAll(firestations);
        }
    }

    public Collection<String> getPhoneNumbersByFirestationNumber(int station) {
        Collection<Firestation> fireStations = firestationRepository.findByStationNumber(station);

        Collection<Person> persons = personService.getByAddressIn(fireStations.stream().map(Firestation::getAddress).toList());

        Collection<String> phoneNumbers = new ArrayList<>();
        for (Person person : persons) {
            phoneNumbers.add(person.getPhone());
        }
        return phoneNumbers.stream().distinct().toList();
    }

    public Map<String, Object> getFirestationResidents(String address) {
        Firestation fireStation = firestationRepository.findByAddress(address)
                .orElseThrow();

        Collection<Person> persons = personService.getByAddress(address);
        Collection<MedicalRecord> medicalRecords = medicalRecordService.getByLastNameIn(persons.stream().map(Person::getLastName).toList());

        return dataMapper.personWithMedicalRecordAndFirestationNumber(persons, medicalRecords, fireStation.getStation());
    }

    public Map<String, Collection<ResidentDTO>> getHousesByFirestations(Collection<Integer > stations) {
        Collection<Firestation> fireStations = firestationRepository.getFirestationsByStationIn(stations);
        Collection<Person> persons = personService.getByAddressIn(fireStations.stream().map(Firestation::getAddress).toList());
        Collection<MedicalRecord> medicalRecords = medicalRecordService.getByLastNameIn(persons.stream().map(Person::getLastName).toList());
        return dataMapper.housesByFirestations(persons, medicalRecords, fireStations);
    }

    public Map<String, Object> getResidentsByFirestation(Integer stationNumber) {
        Collection<Firestation> fireStations = firestationRepository.findByStationNumber(stationNumber);
        Collection<Person> persons = personService.getByAddressIn(fireStations.stream().map(Firestation::getAddress).toList());
        Collection<MedicalRecord> medicalRecords = medicalRecordService.getByLastNameInAndFirstNameIn(
                persons.stream().map(Person::getLastName).toList(),
                persons.stream().map(Person::getFirstName).toList());

        return dataMapper.residentsByFirestation(persons, medicalRecords);
    }

}
