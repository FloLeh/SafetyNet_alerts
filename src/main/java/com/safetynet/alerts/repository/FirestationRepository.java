package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FirestationRepository {
    List<Firestation> findByStationNumber(Integer stationNumber);
    List<Firestation> findAll();

    Optional<Firestation> findByAddress(String address);

    FireStation findFirstByAddress(String address);

    List<FireStation> getFireStationsByStationIn(Collection<String> stations);
}
