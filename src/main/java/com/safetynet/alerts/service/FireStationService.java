package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    public Iterable<FireStation> getAllFireStations() {
        return fireStationRepository.findAll();
    }

    public FireStation saveFireStation(FireStation input) {

        FireStation fireStation = new FireStation();

        fireStation.setAddress(input.getAddress());
        fireStation.setStation(input.getStation());

        return fireStationRepository.save(fireStation);
    }

}
