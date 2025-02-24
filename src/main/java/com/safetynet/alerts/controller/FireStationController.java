package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @GetMapping("/firestations")
    public List<FireStation> getAllFireStations() {
        return fireStationService.getAllFireStations();
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneAlert(@RequestParam String firestation) {
        return fireStationService.getPhoneNumbersByFireStation(firestation);
    }

}
