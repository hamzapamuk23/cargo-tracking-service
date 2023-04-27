package com.agridin.cargotrackingservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agridin.cargotrackingservice.model.CargoTracking;

@Repository
public interface CargoTrackingRepository extends JpaRepository<CargoTracking,UUID> {
    
}
