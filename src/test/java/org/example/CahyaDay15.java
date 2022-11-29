package org.example;

import javafx.util.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

public class CahyaDay15 {
    @Test
    public void postTest(){
        System.setProperty("webdriver.chrome.driver", "C:\\sqa\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        String baseURL = "https://demo.guru99.com/payment-gateway/index.php";
        driver.get(baseURL);

        WebElement selectOption = driver.findElement(By.xpath("//select//option[@value='5']"));
        WebElement buyNow = driver.findElement(By.xpath("//input[@value='Buy Now']"));

        selectOption.click();
        buyNow.click();

        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
        WebElement cardNumber = driver.findElement(By.xpath("//input[@id='card_nmuber']"));
        WebElement expireMonth = driver.findElement(By.xpath("//select//option[@value='2']"));
        WebElement expireYear = driver.findElement(By.xpath("//select[@id='year']//option[7]"));
        WebElement cvvCode = driver.findElement(By.xpath("//input[@id='cvv_code']"));
        WebElement submit = driver.findElement(By.xpath("//input[@type='submit']"));


        cardNumber.sendKeys("6667677788787878");
        expireMonth.click();
        expireYear.click();
        cvvCode.sendKeys("343");
        submit.click();

        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
        WebElement alert = driver.findElement(By.xpath("//h2"));
        String expect = "Payment successfull!";
        String actual = alert.getText().toString();
        asserEquals(actual,expect);

    }

    private void asserEquals(String actual, String expect) {
    }
}
