package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.DataParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class FirestationRepositoryFromJson implements FirestationRepository {

    private final DataParser dataParser;

    public List<Firestation> findByStationNumber(Integer stationNumber){
        return findAll()
                .stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .toList();
    }

    public List<Firestation> findAll(){
        return new ArrayList<>(dataParser.getFirestations());
    }

    public Optional<Firestation> findByAddress(String address){
        return findAll()
                .stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .findFirst();
    }


    public List<Firestation> getFirestationsByStationIn(Collection<Integer> stations){
        return findAll()
                .stream()
                .filter(firestation -> stations.contains(firestation.getStation()))
                        .toList();
    }

    public Firestation save(Firestation firestation){
        dataParser.getFirestations().add(firestation);
        return firestation;
    }

    public void delete(Optional<Firestation> firestation){
        dataParser.getFirestations().remove(firestation.get());
    }

    public void deleteAll(Collection<Firestation> fireStations){
        dataParser.getFirestations().removeAll(fireStations);
    }
}
