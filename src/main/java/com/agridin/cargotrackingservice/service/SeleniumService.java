package com.agridin.cargotrackingservice.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import com.agridin.cargotrackingservice.model.CargoTracking;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SeleniumService {

    private CargoTrackingService cargoTrackingService;

    public void deneme() throws InterruptedException{
        ChromeDriver browser = getChromeDriver();
        browser.get("https://gonderitakip.ptt.gov.tr/");
        wait(300); 

        List<String> deneme =getAllTrackingNumber();

        for (String trackingNo : deneme) {
            WebElement trackingNumber = getWebElementById(browser, "search-area");
            trackingNumber.sendKeys(trackingNo);

            getWebElementById(browser, "searchButton").click();;
    
            List<WebElement> trList = getWebElementByXPath(browser, "//*[@id='shipActivity']/div/div/table/tbody").findElements(By.tagName("tr"));
            for (WebElement trIter : trList) {
                List<WebElement> td = trIter.findElements(By.tagName("td"));
                LocalDateTime localDateTime =localDateTimeParse(getAttributeForInnerText(td.get(0)));
                CargoTracking cargoTracking = new CargoTracking(null, localDateTime,trackingNo, getAttributeForInnerText(td.get(1)),getAttributeForInnerText(td.get(2)), getAttributeForInnerText(td.get(3)), getAttributeForInnerText(td.get(4)));
                cargoTrackingService.save(cargoTracking);
            }
            getWebElementByXPath(browser, "/html/body/main/div/div[1]/div[3]/a").click();
            wait(300);
        }
        browser.close();
    }

    public WebElement getWebElementById(ChromeDriver browser,String value){
        return browser.findElement(By.id(value));
    }

    public WebElement getWebElementByXPath(ChromeDriver browser,String value){
        return browser.findElement(By.xpath(value));
    }

    public ChromeDriver getChromeDriver(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }

    public void wait(int value){
        try {
            Thread.sleep(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LocalDateTime localDateTimeParse(String date){
        DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
    
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
}
