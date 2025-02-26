package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FirestationController {

    private final FirestationService fireStationService;

    @GetMapping("/firestations")
    public List<Firestation> getAllFirestations() {
        return fireStationService.getAllFirestations();
    }

//    @GetMapping("/firestation")
//    public Map<String, Object> getResidentsByStation(@RequestParam String stationNumber) {
//        return fireStationService.getResidentsByFirestation(stationNumber);
//    }

    @PostMapping("/firestation")
    public Firestation addFirestation(@RequestBody Firestation fireStation) {
        return fireStationService.saveFirestation(fireStation);
    }

    @PutMapping("/firestation")
    public Firestation updateFirestation(@RequestBody Firestation fireStation) {
        return fireStationService.updateFirestation(fireStation);
    }

    @DeleteMapping("/firestation")
    public void deleteFirestationByAddress(@RequestParam Optional<String> address, @RequestParam Optional<Integer> stationNumber, @RequestParam String deleteBy) {
        fireStationService.deleteFirestation(address, stationNumber, deleteBy);
    }

//    @GetMapping("/phoneAlert")
//    public List<String> getPhoneAlert(@RequestParam int firestation) {
//        return fireStationService.getPhoneNumbersByFirestationNumber(firestation);
//    }
//
//    @GetMapping("/fire")
//    public Map<String, Object> getFirestationResidents(@RequestParam String address) {
//        return fireStationService.getFirestationResidents(address);
//    }
//
//    @GetMapping("/flood/stations")
//    public Map<String, List<ResidentDTO>> getFirestationResidents(@RequestParam List<String> stations) {
//        return fireStationService.getHousesByFirestations(stations);
//    }

}
