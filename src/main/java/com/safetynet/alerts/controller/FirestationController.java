package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FirestationResidentsByAddressDTO;
import com.safetynet.alerts.dto.FirestationResidentsCountDTO;
import com.safetynet.alerts.dto.FirestationResidentWithMedicalRecordDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FirestationController {

    private final FirestationServiceImpl fireStationService;

    /**
     * Read - Get all firestations
     * @return A List of Firestation objects
     */
    @GetMapping("/firestations")
    public List<Firestation> getAllFirestations() {
        log.info("GET: /firestations");
        return fireStationService.getAllFirestations();
    }

    /**
     * Read - Get residents by station
     * @param stationNumber A station number
     * @return A FirestationResidentsCountDTO
     */
    @GetMapping("/firestation")
    public FirestationResidentsCountDTO getResidentsByStation(@RequestParam int stationNumber) {
        log.info("GET: /firestation?stationNumber={}", stationNumber);
        return fireStationService.getResidentsByFirestation(stationNumber);
    }

    /**
     * Create - Add a new firestation
     * @param fireStation A Firestation object
     * @return The new firestation
     */
    @PostMapping("/firestation")
    public Firestation addFirestation(@RequestBody Firestation fireStation) throws IOException {
        log.info("POST: /firestation");
        return fireStationService.saveFirestation(fireStation);
    }

    /**
     * Update - Save firestation
     * @param fireStation A Firestation object
     * @return The updated firestation
     */
    @PutMapping("/firestation")
    public Firestation updateFirestation(@RequestBody Firestation fireStation) throws IOException {
        log.info("PUT: /firestation");
        return fireStationService.updateFirestation(fireStation);
    }

    /**
     * Delete - Delete a firestation
     * @param address An address to delete one
     * @param stationNumber A station number delete multiple
     */
    @DeleteMapping("/firestation")
    public void deleteFirestationByAddress(@RequestParam(required = false) String address, @RequestParam(required = false) Integer stationNumber) throws IOException {
        if(address == null && stationNumber == null) {
            throw new IllegalArgumentException("Address and stationNumber can't be null");
        }
        log.info("DELETE: /firestation?address={}, stationNumber={}", address, stationNumber);
        fireStationService.deleteFirestation(address, stationNumber);
    }

    /**
     * Read - Get all the phone numbers by firestation
     * @param firestation A station number
     * @return A list of phone numbers
     */
    @GetMapping("/phoneAlert")
    public List<String> getPhoneAlert(@RequestParam int firestation) {
        log.info("GET: /phoneAlert?firestation={}", firestation);
        return fireStationService.getPhoneNumbersByFirestationNumber(firestation);
    }

    /**
     * Read - Get all the residents by firestation
     * @param address An address
     * @return A list of residents with the station number
     */
    @GetMapping("/fire")
    public FirestationResidentsByAddressDTO getFirestationResidents(@RequestParam String address) {
        log.info("GET: /fire?address={}", address);
        return fireStationService.getFirestationResidents(address);
    }

    /**
     * Read - Get houses by firestation
     * @param stations A list of station numbers
     * @return A map with addresses as keys and the according residents as values
     */
    @GetMapping("/flood/stations")
    public Map<String, List<FirestationResidentWithMedicalRecordDTO>> getHousesByFirestations(@RequestParam List<Integer> stations) {
        log.info("GET: /flood/stations?stations={}", stations);
        return fireStationService.getHousesByFirestations(stations);
    }

}
