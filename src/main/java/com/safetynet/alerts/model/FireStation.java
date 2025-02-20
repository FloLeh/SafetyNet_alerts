package com.safetynet.alerts.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "fireStation")
public class FireStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String station;

}
