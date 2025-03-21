package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.DataParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public Firestation findByAddress(String address){
        return findAll()
                .stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid address"));
    }

    public List<Firestation> getFirestationsByStationIn(List<Integer> stations){
        return findAll()
                .stream()
                .filter(firestation -> stations.contains(firestation.getStation()))
                        .toList();
    }

    public Firestation save(Firestation firestation) throws IOException {
        dataParser.getFirestations().add(firestation);
        dataParser.save();
        return firestation;
    }

    public Firestation update(Firestation firestation) throws IOException {
        dataParser.getFirestations()
                .stream()
                .filter(firestationToUpdate -> firestationToUpdate.getAddress().equals(firestation.getAddress()))
                .forEach(firestationToUpdate -> firestationToUpdate.setAddress(firestation.getAddress()));
        dataParser.save();
        return firestation;
    }

    public void delete(Firestation firestation) throws IOException {
        dataParser.getFirestations().remove(firestation);
        dataParser.save();
    }

    public void deleteAll(List<Firestation> fireStations) throws IOException {
        dataParser.getFirestations().removeAll(fireStations);
        dataParser.save();
    }
}
