package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FirestationResidentsByAddressDTO;
import com.safetynet.alerts.dto.FirestationResidentsCountDTO;
import com.safetynet.alerts.dto.FirestationResidentDTO;
import com.safetynet.alerts.dto.FirestationResidentWithMedicalRecordDTO;
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
public class FirestationServiceImpl implements FirestationService {

    private final FirestationRepository firestationRepository;
    private final PersonServiceImpl personService;
    private final MedicalRecordServiceImpl medicalRecordService;

    public List<Firestation> getAllFirestations() {
        return firestationRepository.findAll();
    }

    public Firestation saveFirestation(final Firestation input) throws IOException {
        return firestationRepository.save(input);
    }

    public Firestation updateFirestation(final Firestation input) throws IOException {
        Assert.notNull(input.getStation(), "station is required");
        Assert.hasText(input.getAddress(), "address is required");

        Firestation fireStation = firestationRepository.findByAddress(input.getAddress());

         fireStation.setStation(input.getStation());
         return firestationRepository.update(fireStation);
    }

    public void deleteFirestation(final String address, final Integer stationNumber) throws IOException {
        if (address == null || address.isEmpty()) {
            final List<Firestation> firestations = firestationRepository.findAll()
                    .stream()
                    .filter(firestation -> firestation.getStation().equals(stationNumber))
                    .toList();
            firestationRepository.deleteAll(firestations);

        } else {
            final Firestation firestation = firestationRepository.findByAddress(address);
            firestationRepository.delete(firestation);
        }
    }

    public List<String> getPhoneNumbersByFirestationNumber(int station) {
        List<Firestation> fireStations = firestationRepository.findByStationNumber(station);

        List<Person> persons = personService.getByAddressIn(fireStations.stream().map(Firestation::getAddress).toList());

        List<String> phoneNumbers = new ArrayList<>();
        for (Person person : persons) {
            phoneNumbers.add(person.getPhone());
        }
        return phoneNumbers.stream().distinct().toList();
    }

    public FirestationResidentsByAddressDTO getFirestationResidents(String address) {
        Firestation fireStation = firestationRepository.findByAddress(address);

        List<Person> persons = personService.getByAddress(address);
        List<MedicalRecord> medicalRecords = medicalRecordService.getByLastNameIn(persons.stream().map(Person::getLastName).toList());

        return personWithMedicalRecordAndFirestationNumber(persons, medicalRecords, fireStation.getStation());
    }

    public Map<String, List<FirestationResidentWithMedicalRecordDTO>> getHousesByFirestations(List<Integer > stations) {
        List<Firestation> fireStations = firestationRepository.getFirestationsByStationIn(stations);
        List<Person> persons = personService.getByAddressIn(fireStations.stream().map(Firestation::getAddress).toList());
        List<MedicalRecord> medicalRecords = medicalRecordService.getByLastNameIn(persons.stream().map(Person::getLastName).toList());
        return housesByFirestations(persons, medicalRecords, fireStations);
    }

    public FirestationResidentsCountDTO getResidentsByFirestation(Integer stationNumber) {
        List<Firestation> fireStations = firestationRepository.findByStationNumber(stationNumber);
        List<Person> persons = personService.getByAddressIn(fireStations.stream().map(Firestation::getAddress).toList());
        List<MedicalRecord> medicalRecords = medicalRecordService.getByLastNameInAndFirstNameIn(
                persons.stream().map(Person::getLastName).toList(),
                persons.stream().map(Person::getFirstName).toList());

        return residentsByFirestation(persons, medicalRecords);
    }

    public List<FirestationResidentWithMedicalRecordDTO> residentsByMedicalRecord(List<Person> persons, List<MedicalRecord> medicalRecords) {
        return persons.stream().map(person -> {
            final MedicalRecord medicalRecord = medicalRecords.stream().filter(m -> m.getFirstName().equals(person.getFirstName())).findFirst().orElseThrow();
            return new FirestationResidentWithMedicalRecordDTO(person, medicalRecord);
        }).toList();
    }

    public Map<String, List<FirestationResidentWithMedicalRecordDTO>> housesByFirestations(List<Person> persons, List<MedicalRecord> medicalRecords, List<Firestation> fireStations) {
        Map<String, List<FirestationResidentWithMedicalRecordDTO>> residentsByAddress = new HashMap<>();
        List<String> addresses = fireStations.stream().map(Firestation::getAddress).toList();

        for (String address : addresses) {
            List<Person> personsFiltered = persons.stream().filter(person -> person.getAddress().equals(address)).toList();
            List<FirestationResidentWithMedicalRecordDTO> residents = residentsByMedicalRecord(personsFiltered, medicalRecords);

            residentsByAddress.put(address, residents);
        }

        return residentsByAddress;
    }

    public FirestationResidentsByAddressDTO personWithMedicalRecordAndFirestationNumber(List<Person> persons, List<MedicalRecord> medicalRecords, int station) {
        List<FirestationResidentWithMedicalRecordDTO> residents = residentsByMedicalRecord(persons, medicalRecords);
        return new FirestationResidentsByAddressDTO(residents, station);
    }

    public FirestationResidentsCountDTO residentsByFirestation(List<Person> persons, List<MedicalRecord> medicalRecords) {
        List<FirestationResidentDTO> residents = persons.stream()
                .map(person -> new FirestationResidentDTO(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getPhone()
                )).toList();

        int adults = medicalRecords.stream().filter(MedicalRecord::isMajor).toList().size();
        int children = medicalRecords.size() - adults;

        return new FirestationResidentsCountDTO(residents, adults, children);
    }

}
