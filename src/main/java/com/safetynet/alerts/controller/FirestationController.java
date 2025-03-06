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

    @GetMapping("/firestations")
    public List<Firestation> getAllFirestations() {
        return fireStationService.getAllFirestations();
    }

    @GetMapping("/firestation")
    public FirestationResidentsCountDTO getResidentsByStation(@RequestParam int stationNumber) {
        return fireStationService.getResidentsByFirestation(stationNumber);
    }

    @PostMapping("/firestation")
    public Firestation addFirestation(@RequestBody Firestation fireStation) throws IOException {
        return fireStationService.saveFirestation(fireStation);
    }

    @PutMapping("/firestation")
    public Firestation updateFirestation(@RequestBody Firestation fireStation) throws IOException {
        return fireStationService.updateFirestation(fireStation);
    }

    @DeleteMapping("/firestation")
    public void deleteFirestationByAddress(@RequestParam(required = false) String address, @RequestParam(required = false) Integer stationNumber) throws IOException {
        if(address == null && stationNumber == null) {
            throw new IllegalArgumentException("Address and stationNumber can't be null");
        }
        fireStationService.deleteFirestation(address, stationNumber);
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneAlert(@RequestParam int firestation) {
        return fireStationService.getPhoneNumbersByFirestationNumber(firestation);
    }

    @GetMapping("/fire")
    public FirestationResidentsByAddressDTO getFirestationResidents(@RequestParam String address) {
        return fireStationService.getFirestationResidents(address);
    }

    @GetMapping("/flood/stations")
    public Map<String, List<FirestationResidentWithMedicalRecordDTO>> getFirestationResidents(@RequestParam List<Integer> stations) {
        return fireStationService.getHousesByFirestations(stations);
    }

}
