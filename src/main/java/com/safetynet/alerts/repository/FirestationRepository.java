package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface FirestationRepository {
    Collection<Firestation> findAll();

    Firestation save(Firestation fireStation) throws IOException;
    Firestation update(Firestation fireStation) throws IOException;
    void delete(Optional<Firestation> fireStation) throws IOException;
    void deleteAll(Collection<Firestation> fireStations) throws IOException;

    Optional<Firestation> findByAddress(String address);
    Collection<Firestation> findByStationNumber(Integer stationNumber);
    Collection<Firestation> getFirestationsByStationIn(Collection<Integer> stations);
}
