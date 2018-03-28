package test.ekatalog.belousov;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.testng.ITestResult;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected WebDriver driver;
    protected String userName = "tortyt1@gmail.com";
    protected String password = "Qwerty123456";

    public WebDriver getDriver() {
        return driver;
    }

    public  void InitChrome() {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir") + "\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void createDriver() {
        InitChrome();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDownDriver() {
        if (driver != null)
        {
            driver.findElement(By.xpath("//a[@jtarget='mui_user_login_row']")).click();
            try
            {
                driver.quit();
            }
            catch (WebDriverException e) {
                System.out.println("***** CAUGHT EXCEPTION IN DRIVER TEARDOWN *****");
                System.out.println(e);
            }
        }
    }

    public WebElement fluentWait(final By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(15, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });

        return  foo;
    };
}

