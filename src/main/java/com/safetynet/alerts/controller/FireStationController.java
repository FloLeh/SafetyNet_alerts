package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ResidentDTO;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
