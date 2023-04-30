package com.agridin.cargotrackingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agridin.cargotrackingservice.service.SeleniumAgridinService;
import com.agridin.cargotrackingservice.service.SeleniumPttService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("cargoTrackingController")
public class CargoTrackingController {

    private SeleniumPttService seleniumPttService;

    private SeleniumAgridinService seleniumAgridinService;

    @GetMapping("/deneme")
    public void get(){
        seleniumPttService.deneme();
    }

    @PostMapping("/post")
    public void deneme(@RequestBody String value){
        seleniumAgridinService.deneme(value);
    }
}
