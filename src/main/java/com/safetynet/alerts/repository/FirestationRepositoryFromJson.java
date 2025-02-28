package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.DataParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class FirestationRepositoryFromJson implements FirestationRepository {

    private final DataParser dataParser;

    public Collection<Firestation> findByStationNumber(Integer stationNumber){
        return findAll()
                .stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .toList();
    }

    public Collection<Firestation> findAll(){
        return new ArrayList<>(dataParser.getFirestations());
    }

    public Optional<Firestation> findByAddress(String address){
        return findAll()
                .stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .findFirst();
    }

    public Collection<Firestation> getFirestationsByStationIn(Collection<Integer> stations){
        return findAll()
                .stream()
                .filter(firestation -> stations.contains(firestation.getStation()))
                        .toList();
    }

    public Firestation save(Firestation firestation) throws IOException {
        dataParser.getFirestations().add(firestation);
        dataParser.saveIntoJsonFile();
        return firestation;
    }
    public Firestation update(Firestation firestation) throws IOException {
        dataParser.getFirestations()
                .stream()
                .filter(firestationToUpdate -> firestationToUpdate.getAddress().equals(firestation.getAddress()))
                .forEach(firestationToUpdate -> firestationToUpdate.setAddress(firestation.getAddress()));
        dataParser.saveIntoJsonFile();
        return firestation;
    }

    public void delete(Optional<Firestation> firestation) throws IOException {
        dataParser.getFirestations().remove(firestation.get());
        dataParser.saveIntoJsonFile();
    }

    public void deleteAll(Collection<Firestation> fireStations) throws IOException {
        dataParser.getFirestations().removeAll(fireStations);
        dataParser.saveIntoJsonFile();
    }
}
