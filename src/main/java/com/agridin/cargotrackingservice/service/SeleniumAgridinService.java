package com.agridin.cargotrackingservice.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.agridin.cargotrackingservice.model.Order;
import com.agridin.cargotrackingservice.model.dto.AddCargoTrackingDto;
import com.agridin.cargotrackingservice.model.dto.LoginDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SeleniumAgridinService {

    private OrderService orderService;

    private SeleniumPttService seleniumPttService;

    public void deneme(AddCargoTrackingDto request){
        String[] lines = request.getCargoTrackingNumbers().split("\n");

        ChromeDriver browser = getChromeDriver();
        browser.get("https://agridinkremi.net/security/login");
        
        getWebElementById(browser, "EmailAdresi").sendKeys(request.getUsername());
        getWebElementById(browser, "Parola").sendKeys(request.getPassword());


        getWebElementByXPath(browser, "/html/body/div[1]/div[2]/form/div[3]/div/button").click();

        browser.get("https://agridinkremi.net/Siparis/CompletedList");
        for (int i = 0; i < lines.length; i++) {
            if(lines[i] != ""){
                String[] parts = lines[i].split("\\s+", 2);
                String trackingNumber = parts[0];
                String nameSurname = parts[1];

                getWebElementByXPath(browser, "//*[@id='example1_filter']/label/input").sendKeys(nameSurname);
                List<WebElement> orderWebElementList = getWebElementByXPath(browser, "//*[@id='example1']/tbody").findElements(By.tagName("tr"));
                if(!orderWebElementList.get(0).getAttribute("innerText").equals("Üzgünüm aradığınız kelimeyle eşleşen kayıt bulunmamaktadır.")) {
                    List<WebElement> td = orderWebElementList.get(0).findElements(By.tagName("td"));
                    Order order = new Order(null,
                        new Date(),
                        trackingNumber,
                        "",
                        getAttributeForInnerText(td.get(1)),
                        getAttributeForInnerText(td.get(2)),
                        getAttributeForInnerText(td.get(3)),
                        getAttributeForInnerText(td.get(4)),
                        getAttributeForInnerText(td.get(5)),
                        getAttributeForInnerText(td.get(6)),
                        getAttributeForInnerText(td.get(7)),
                        getAttributeForInnerText(td.get(9)),
                        localDateTimeParse(getAttributeForInnerText(td.get(10)))
                        );
                    orderService.save(order);
                }
                getWebElementByXPath(browser, "//*[@id='example1_filter']/label/input").clear();
            }
        }
        browser.close();
        seleniumPttService.getCargoTracking();
    }

    public WebElement getWebElementById(ChromeDriver browser,String value){
        return browser.findElement(By.id(value));
    }

    public WebElement getWebElementByXPath(ChromeDriver browser,String value){
        return browser.findElement(By.xpath(value));
    }

    public ChromeDriver getChromeDriver(){
        // //ChromeOptions options = new ChromeOptions();
        // //options.addArguments("--headless");
        return new ChromeDriver();
    }

    public void wait(int value){
        try {
            Thread.sleep(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LocalDateTime localDateTimeParse(String date){
        DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    
        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String newString = LocalDateTime.parse(date, oldFormat).format(newFormat);
    
        LocalDateTime localDateTime = LocalDateTime.parse(newString);
        return localDateTime;
    }

    public String getAttributeForInnerText(WebElement element){
        return element.getAttribute("innerText");
    }

    public List<String> getAllTrackingNumber(){
        List<String> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get("/Users/Hamza/Desktop/Adsız.txt"))) {
            list = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void agridinLogin(LoginDto loginDto){

        ChromeDriver browser = getChromeDriver();
        browser.get("https://agridinkremi.net/security/login");
        
        getWebElementById(browser, "EmailAdresi").sendKeys(loginDto.getUsername());
        getWebElementById(browser, "Parola").sendKeys(loginDto.getPassword());
        getWebElementByXPath(browser, "/html/body/div[1]/div[2]/form/div[3]/div/button").click();

        
        if(!browser.getCurrentUrl().equals("https://agridinkremi.net/Siparis")){
            browser.close();
            throw new RuntimeException("Kullanıcı Adı veya Parola Yanlış");
        }

    }
}
