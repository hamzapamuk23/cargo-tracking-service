package com.agridin.cargotrackingservice.service;

import org.springframework.stereotype.Service;

import com.agridin.cargotrackingservice.model.CargoTracking;
import com.agridin.cargotrackingservice.repository.CargoTrackingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CargoTrackingService {
    
    private CargoTrackingRepository repository;

    public CargoTracking save(CargoTracking cargoTracking){
        return repository.save(cargoTracking);
    }

}
