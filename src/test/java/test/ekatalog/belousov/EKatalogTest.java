package test.ekatalog.belousov;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
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

@Listeners({ Listener.class })
public class EKatalogTest extends  BaseTest{

	@Test(description = "Success login user test")
	public void loginUser() {
		driver.get("http://www.e-katalog.ru/");
		fluentWait(By.className("wu_entr")).click();
		WebElement loginInput = fluentWait(By.name("l_"));
		loginInput.sendKeys("tortyt1@gmail.com");
		WebElement passwordInput = fluentWait(By.name("pw_"));
		passwordInput.sendKeys(password);
		WebElement inputEnter = fluentWait(By.className("l-but"));
		inputEnter.click();
		boolean resultActual = fluentWait(By.xpath("//a[@href='/ek-wu.php?idwu_=fpjtspxok2g&view_=user']"))!= null;
		boolean resultExpected = true;
		Assert.assertEquals(resultActual, resultExpected);
	}

	@Test(description = "Success search product \"Sony\" test",
			dependsOnMethods = "loginUser")
	public void checkSearchSony() throws InterruptedException {
		loginUser();
		WebElement menu = fluentWait(By.xpath("//a[@href='/k119.htm']"));
		Actions builder = new Actions(driver);
		builder.moveToElement(menu)
				.click(menu);
		Action mouseoverAndClick = builder.build();
		mouseoverAndClick.perform();
		Thread.sleep(500);
		WebElement actionCamera = fluentWait(By.xpath("//a[@href='/k695.htm']"));
		actionCamera.click();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		WebElement sonyCheckbox = fluentWait(By.id("br156"));
		executor.executeScript("arguments[0].click()", sonyCheckbox);
		WebElement nfsCheckbox = fluentWait(By.id("c17983"));
		executor.executeScript("arguments[0].click()", nfsCheckbox);
		WebElement submitButton = fluentWait(By.id("match_submit"));
		executor.executeScript("arguments[0].click()", submitButton);
		WebElement cameraInfo = fluentWait(By.id("product_625607"));
		executor.executeScript("arguments[0].click()", cameraInfo);
		fluentWait(By.className("big-star-off")).click();
		boolean actualResult = fluentWait(By.id("marked_item_625607")) != null;
		Assert.assertEquals(actualResult, true);
		fluentWait(By.id("name_bm_visited")).click();
		actualResult = fluentWait(By.xpath("//div[text()='Sony HDR-AZ1VW']")) != null;
		Assert.assertEquals(actualResult, true);
		WebElement deleteItem = fluentWait(By.className("big-star-on"));
		deleteItem.click();
	}

	@Test(description = "Success filtering computers by price test",
			dependsOnMethods = "loginUser")
	public void checkSearchComputers() throws InterruptedException {
		loginUser();
		String MaxPrice = "10000";
		driver.findElement(By.xpath("//a[@href='/list/30/']")).click();
		WebElement maxPriceInput = driver.findElement(By.name("maxPrice_"));
		maxPriceInput.clear();
		maxPriceInput.sendKeys(MaxPrice);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		WebElement submitButton = driver.findElement(By.id("match_submit"));
		executor.executeScript("arguments[0].click()", submitButton);
		int maxPrice = Integer.parseInt(MaxPrice);
		List<WebElement> linksToClick = driver.findElements(By.className("model-price-range"));
		for (WebElement priceItem : linksToClick) {
			String priceStr = priceItem.findElements(By.tagName("span")).get(0).getText();
			String priceStrWithoutSpaces = priceStr.replaceAll(" ", "");
			int priceInt = Integer.parseInt(priceStrWithoutSpaces);
			Assert.assertTrue(priceInt < maxPrice, String.valueOf(priceInt));
		}
	}
}
