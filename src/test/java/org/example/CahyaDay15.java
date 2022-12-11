package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import javafx.scene.layout.Priority;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static org.testng.AssertJUnit.assertEquals;

import java.sql.Driver;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class CahyaDay15 {
    public static ExtentTest test;
    //builds a new report using the html template
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    private ChromeDriver driver;
    //helps to generate the logs in test report.


    @BeforeTest
    public void startReport() {
        // initialize the HtmlReporter
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/testReport.html");

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        //configuration items to change the look and feel
        //add content, manage tests etc
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Extent Report Demo");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }

    @BeforeSuite
    public void StartRunner() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:\\sqa\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        driver.get("https://demo.guru99.com/payment-gateway/index.php");
        driver.manage().window().maximize();
        Thread.sleep(3000);
    }

    @Test (priority = 1)
    public void PagePayment() throws InterruptedException{
        driver.findElement(By.xpath("//select//option[@value='5']")).click();
        test = extent.createTest("Select Option Quantity", "PASSED test case");
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@value='Buy Now']")).click();
        test = extent.createTest("Buy Now", "PASSED test case");
        Thread.sleep(500);
    }

    @Test (priority = 2)
    public void PageProcces() throws InterruptedException{
        driver.findElement(By.xpath("//input[@id='card_nmuber']")).sendKeys("6667677788787878");
        test = extent.createTest("Input Card Number", "PASSED test case");
        Thread.sleep(500);
        driver.findElement(By.xpath("//select//option[@value='2']")).click();
        test = extent.createTest("Input Expire Month", "PASSED test case");
        Thread.sleep(500);
        driver.findElement(By.xpath("//select[@id='year']//option[7]")).click();
        test = extent.createTest("Input Expire Year", "PASSED test case");
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@id='cvv_code']")).sendKeys("343");
        test = extent.createTest("Input CVV Code", "PASSED test case");
        Thread.sleep(500);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        test = extent.createTest("Payment", "PASSED test case");
        Thread.sleep(500);
    }

    @Test (priority = 3)
    public void succesfull() throws InterruptedException{
        WebElement alert = driver.findElement(By.cssSelector("div[class='table-wrapper'] h2"));
        String expect = "Payment successfull!";
        assertEquals(alert.getText(),expect);

        test = extent.createTest("Payment Gateway", "PASSED test case");
        Assert.assertTrue(true);

        driver.quit();
    }

//    @Test
//    public void postTest(){
//        System.setProperty("webdriver.chrome.driver", "C:\\sqa\\chromedriver_win32\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        String baseURL = "https://demo.guru99.com/payment-gateway/index.php";
//        driver.get(baseURL);
//        driver.manage().window().maximize();
//
//        WebElement selectOption = driver.findElement(By.xpath("//select//option[@value='5']"));
//        WebElement buyNow = driver.findElement(By.xpath("//input[@value='Buy Now']"));
//
//        selectOption.click();
//        buyNow.click();
//
//        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
//        WebElement cardNumber = driver.findElement(By.xpath("//input[@id='card_nmuber']"));
//        WebElement expireMonth = driver.findElement(By.xpath("//select//option[@value='2']"));
//        WebElement expireYear = driver.findElement(By.xpath("//select[@id='year']//option[7]"));
//        WebElement cvvCode = driver.findElement(By.xpath("//input[@id='cvv_code']"));
//        WebElement submit = driver.findElement(By.xpath("//input[@type='submit']"));
//
//
//        cardNumber.sendKeys("6667677788787878");
//        expireMonth.click();
//        expireYear.click();
//        cvvCode.sendKeys("343");
//        submit.click();
//
//        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
//        WebElement alert = driver.findElement(By.cssSelector("div[class='table-wrapper'] h2"));
//
//        String expect = "Payment successfull!";
//
//        assertEquals(alert.getText(),expect);
//
//        test = extent.createTest("Payment Gateway", "PASSED test case");
//        Assert.assertTrue(true);
//
//        driver.quit();
//    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
        }
        else {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }
    }

    @AfterTest
    public void tearDown() {
        //to write or update test information to reporter
        extent.flush();
    }
}
