package com.agridin.cargotrackingservice.controller;

import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agridin.cargotrackingservice.model.dto.LoginDto;
import com.agridin.cargotrackingservice.service.SeleniumAgridinService;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("seleniumAgridin")
public class SeleniumAgridinController {

    private SeleniumAgridinService seleniumAgridinService;

    @PostMapping("/login")
    public void loginAgridin(@RequestBody LoginDto loginDto){
        seleniumAgridinService.agridinLogin(loginDto);    }

    
}
