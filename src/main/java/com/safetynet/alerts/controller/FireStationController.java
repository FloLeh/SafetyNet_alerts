package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ResidentDTO;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @GetMapping("/firestations")
    public List<FireStation> getAllFireStations() {
        return fireStationService.getAllFireStations();
    }

    @GetMapping("/firestation")
    public Map<String, Object> getResidentsByStation(@RequestParam String stationNumber) {
        return fireStationService.getResidentsByFireStation(stationNumber);
    }

    @PostMapping("/firestation")
    public FireStation addFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.saveFireStation(fireStation);
    }

    @PutMapping("/firestation")
    public FireStation updateFireStation(@RequestBody FireStation fireStation) {
        return fireStationService.updateFireStation(fireStation);
    }

    @DeleteMapping("/firestation")
    public void deleteFireStationByAddress(@RequestBody FireStation fireStation) {
        fireStationService.deleteFireStation(fireStation);
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneAlert(@RequestParam String firestation) {
        return fireStationService.getPhoneNumbersByFireStation(firestation);
    }

    @GetMapping("/fire")
    public Map<String, Object> getFireStationResidents(@RequestParam String address) {
        return fireStationService.getFireStationResidents(address);
    }

    @GetMapping("/flood/stations")
    public Map<String, List<ResidentDTO>> getFireStationResidents(@RequestParam List<String> stations) {
        return fireStationService.getHousesByFireStations(stations);
    }

}
