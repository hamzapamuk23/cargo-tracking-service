package com.agridin.cargotrackingservice.service;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SeleniumService {
    
    public WebDriver webDriver;

    public void deneme(){
        webDriver.close();
        webDriver.get("https://gonderitakip.ptt.gov.tr/");
    }
}
