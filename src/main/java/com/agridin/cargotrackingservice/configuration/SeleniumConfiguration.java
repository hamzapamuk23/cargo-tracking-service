package com.agridin.cargotrackingservice.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SeleniumConfiguration {
    
    @Bean
    public WebDriver getChromeDriver(){
       //System.setProperty("webdriver.chrome.driver","src/main/resources/static/driver/chromedriver");
        ChromeDriver driver = new ChromeDriver();
        return driver;
        
    }
}
