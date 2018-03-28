package test.ekatalog.belousov;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


public class Listener extends TestListenerAdapter {

    @Attachment
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
