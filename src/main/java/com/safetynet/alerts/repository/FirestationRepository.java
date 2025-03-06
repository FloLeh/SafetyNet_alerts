package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public interface FirestationRepository {
    List<Firestation> findAll();

    Firestation save(Firestation fireStation) throws IOException;
    Firestation update(Firestation fireStation) throws IOException;
    void delete(Firestation fireStation) throws IOException;
    void deleteAll(List<Firestation> fireStations) throws IOException;

    Firestation findByAddress(String address);
    List<Firestation> findByStationNumber(Integer stationNumber);
    List<Firestation> getFirestationsByStationIn(List<Integer> stations);
}
