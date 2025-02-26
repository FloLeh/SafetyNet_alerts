package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface FirestationRepository {
    List<Firestation> findByStationNumber(Integer stationNumber);

    List<Firestation> findAll();

    Optional<Firestation> findByAddress(String address);

    List<Firestation> getFirestationsByStationIn(Collection<Integer> stations);

    Firestation save(Firestation fireStation) throws IOException;

    Firestation update(Firestation fireStation) throws IOException;

    void delete(Optional<Firestation> fireStation) throws IOException;

    void deleteAll(Collection<Firestation> fireStations) throws IOException;
}
