package test.ekatalog.belousov;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Method;

public class Listener implements ITestListener {

    @Override
    public void onTestStart(ITestResult iTestResult) {
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            takeScreenShot(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (result.getThrowable() != null) {
            result.getThrowable().printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private  void takeScreenShot(ITestResult tr) throws InterruptedException {
        Object currentClass = tr.getInstance();
        WebDriver webDriver = ((BaseTest) currentClass).getDriver();
        if (webDriver != null) {
            System.out.println("Screesnshot captured for test case:" + tr.getMethod().getMethodName());
            captureScreenshot(webDriver);
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] captureScreenshot(WebDriver d) throws InterruptedException {
        Thread.sleep(1500);
        return ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);
    }
}


/*
public class Listener extends TestListenerAdapter {

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] captureScreenshot(WebDriver d) throws InterruptedException {
        Thread.sleep(1500);
        return ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        try {
            TakeScreenShot(tr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        try {
            TakeScreenShot(tr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private  void TakeScreenShot(ITestResult tr) throws InterruptedException {
        Object currentClass = tr.getInstance();
        WebDriver webDriver = ((BaseTest) currentClass).getDriver();
        if (webDriver != null) {
            System.out.println("Screesnshot captured for test case:" + tr.getMethod().getMethodName());
            captureScreenshot(webDriver);
        }
    }
}
*/