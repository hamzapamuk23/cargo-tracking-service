package com.agridin.cargotrackingservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agridin.cargotrackingservice.model.CargoTracking;
import com.agridin.cargotrackingservice.model.dto.AddCargoTrackingDto;
import com.agridin.cargotrackingservice.model.dto.LoginDto;
import com.agridin.cargotrackingservice.service.CargoTrackingService;
import com.agridin.cargotrackingservice.service.SeleniumAgridinService;
import com.agridin.cargotrackingservice.service.SeleniumPttService;

import lombok.AllArgsConstructor;


@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("cargoTracking")
public class CargoTrackingController {

    private SeleniumPttService seleniumPttService;

    private SeleniumAgridinService seleniumAgridinService;

    private CargoTrackingService cargoTrackingService;

    @GetMapping("/gethCargoTracking")
    public void gethCargoTracking(){
        seleniumPttService.getCargoTracking();
    }

    @PostMapping("/sendCargoTrackingNumbers")
    public void deneme(@RequestBody AddCargoTrackingDto request){
        seleniumAgridinService.deneme(request);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CargoTracking>> getAll() {
        return new ResponseEntity<List<CargoTracking>>(cargoTrackingService.getAll(),HttpStatus.OK);
    }
    
}
