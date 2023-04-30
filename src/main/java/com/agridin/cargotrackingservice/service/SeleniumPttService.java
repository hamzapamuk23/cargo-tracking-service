package com.agridin.cargotrackingservice.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.agridin.cargotrackingservice.model.CargoTracking;
import com.agridin.cargotrackingservice.model.Order;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SeleniumPttService {

    private CargoTrackingService cargoTrackingService;

    private OrderService orderService;

    public void deneme(){
        WebDriver browser = getWebDriver();
        browser.get("https://gonderitakip.ptt.gov.tr/");
        deneme2(browser);
        
        browser.close();
    }

    public void deneme2(WebDriver browser){
        List<Order> orderList = orderService.getAll();
        
        for (Order order : orderList) {
            
            if(!order.getState().equals("TESLİM EDİLDİ")){
                boolean check = true;
                while(check){
                    try {
                        cargoTrackingService.deleteAllByOrder(order.getId());
                        check = false;
                    } catch (Exception e) {
                        check = true;
                    }
                }
                WebElement trackingNumber = getWebElementById(browser, "search-area");
                trackingNumber.sendKeys("kp" + order.getTrackingNumber());
                WebElement searchButton = getWebElementById(browser, "searchButton");
                searchButton.click();
                String cargoState = getAttributeForInnerText(getWebElementByXPath(browser, "/html/body/main/div/div[1]/div[2]/b/h8"));
                orderService.setState(order,cargoState);

                List<WebElement> trList = getWebElementByXPath(browser, "//*[@id='shipActivity']/div/div/table/tbody").findElements(By.tagName("tr"));
                for (WebElement trIter : trList) {
                    List<WebElement> td = trIter.findElements(By.tagName("td"));
                    LocalDateTime localDateTime =localDateTimeParse(getAttributeForInnerText(td.get(0)));
                    CargoTracking cargoTracking = new CargoTracking(null, localDateTime, getAttributeForInnerText(td.get(1)),getAttributeForInnerText(td.get(2)), getAttributeForInnerText(td.get(3)), getAttributeForInnerText(td.get(4)),order);
                    cargoTrackingService.save(cargoTracking);
                }
                browser.manage().deleteAllCookies();
                browser.get("https://gonderitakip.ptt.gov.tr/");
                wait(2000);
                //getWebElementByXPath(browser, "/html/body/main/div/div[1]/div[3]/a").click();
            }
        }
    }

    public WebElement getWebElementById(WebDriver browser,String value){
        return browser.findElement(By.id(value));
    }

    public WebElement getWebElementByXPath(WebDriver browser,String value){
        return browser.findElement(By.xpath(value));
    }

    public ChromeDriver getWebDriver(){
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
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

    public static void clickAndRetry(WebDriver driver, WebElement element) {
        int maxTries = 3;
        int tryCount = 0;
        boolean clicked = false;
        while (tryCount < maxTries && !clicked) {
            try {
                element.click();
                Duration oneSecond = Duration.ofSeconds(1);
                WebDriverWait wait = new WebDriverWait(driver, oneSecond); // 1 saniye bekleyeceğiz
                wait.until(ExpectedConditions.urlContains("https://gonderitakip.ptt.gov.tr/Track/summaryResult")); // beklenen sayfanın URL'sini kontrol ediyoruz
                clicked = true;
            } catch (Exception e) {
                System.err.println("Tıklama işlemi başarısız oldu. Hata: " + e.getMessage());
                driver.navigate().refresh();
                tryCount++;
            }
        }
        if (!clicked) {
            throw new RuntimeException("Tıklama işlemi başarısız oldu.");
        }
    }
}
