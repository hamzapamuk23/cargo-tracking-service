package com.agridin.cargotrackingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agridin.cargotrackingservice.service.SeleniumService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("cargoTrackingController")
public class CargoTrackingController {

    private SeleniumService seleniumService;
    
    @GetMapping("/deneme")
    public void deneme(){
        seleniumService.deneme();
    }
}
