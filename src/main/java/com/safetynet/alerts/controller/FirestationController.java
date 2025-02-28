package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ResidentDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FirestationController {

    private final FirestationService fireStationService;

    @GetMapping("/firestations")
    public Collection<Firestation> getAllFirestations() {
        return fireStationService.getAllFirestations();
    }

    @GetMapping("/firestation")
    public Map<String, Object> getResidentsByStation(@RequestParam int stationNumber) {
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
    public void deleteFirestationByAddress(@RequestParam Optional<String> address, @RequestParam Optional<Integer> stationNumber, @RequestParam String deleteBy) throws IOException {
        fireStationService.deleteFirestation(address, stationNumber, deleteBy);
    }

    @GetMapping("/phoneAlert")
    public Collection<String> getPhoneAlert(@RequestParam int firestation) {
        return fireStationService.getPhoneNumbersByFirestationNumber(firestation);
    }

    @GetMapping("/fire")
    public Map<String, Object> getFirestationResidents(@RequestParam String address) {
        return fireStationService.getFirestationResidents(address);
    }

    @GetMapping("/flood/stations")
    public Map<String, Collection<ResidentDTO>> getFirestationResidents(@RequestParam Collection<Integer> stations) {
        return fireStationService.getHousesByFirestations(stations);
    }

}
