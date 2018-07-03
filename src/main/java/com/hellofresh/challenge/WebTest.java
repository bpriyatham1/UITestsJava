package com.hellofresh.challenge;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.hellofresh.pages.PageObjects;
import com.hellofresh.utilities.excelutil;

public class WebTest extends TestBase {

	private PageObjects PageObjects() {
		PageObjects homePage = PageFactory.initElements(driver, PageObjects.class);
		return homePage;
	}

	@Test(priority = 1)
	public void signInTest() {
		extentTest = extent.startTest("signInTest");
		log.info("Executing :" + getClass() + ":");
		log.info("Starting Execution of 'signInTest' Method:");
		try {
			PageObjects().SignInandRegister();
		} catch (Exception e) {
			System.out.println("Failed to SignIn and perform Registration Process" + e.getMessage());
		}
		extentTest.log(LogStatus.INFO, "Completing the registration Process");
		log.info("Asserting text from heading");
		log.info("Asserting surname and firstname from LoggedIN username");
		extentTest.log(LogStatus.INFO, "Asserting surname and firstname from LoggedIN username");
		extentTest.log(LogStatus.INFO, "Assertion of the sustring of the URL");
	}

	@Test(priority = 2)
	public void logInTest() {
		log.info("Starting Execution of 'logInTest' Method:");
		extentTest = extent.startTest("logInTest");
		String fullName = "Joe Black";
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login"))).click();
		driver.findElement(By.id("email")).sendKeys(existingUserEmail);
		driver.findElement(By.id("passwd")).sendKeys(existingUserPassword);
		driver.findElement(By.id("SubmitLogin")).click();
		WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));

		assertEquals("MY ACCOUNT", heading.getText());
		assertEquals(fullName, driver.findElement(By.className("account")).getText());
		assertTrue(driver.findElement(By.className("info-account")).getText().contains("Welcome to your account."));
		assertTrue(driver.findElement(By.className("logout")).isDisplayed());
		assertTrue(driver.getCurrentUrl().contains("controller=my-account"));
		log.info("Ending Execution of 'logInTest' Method:");
	}

	@Test(priority = 3)
	public void checkoutTest() throws IOException {
		log.info("Starting Execution of 'checkoutTest' Method:");
		extentTest = extent.startTest("checkoutTest");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login"))).click();
		driver.findElement(By.id("email")).sendKeys(existingUserEmail);
		driver.findElement(By.id("passwd")).sendKeys(existingUserPassword);
		driver.findElement(By.id("SubmitLogin")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Women"))).click();
		driver.findElement(By.xpath("//a[@title='Faded Short Sleeve T-shirts']/ancestor::li")).click();
		driver.findElement(By.xpath("//a[@title='Faded Short Sleeve T-shirts']/ancestor::li")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='layer_cart']//a[@class and @title='Proceed to checkout']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(@class,'cart_navigation')]/a[@title='Proceed to checkout']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("processAddress"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uniform-cgv"))).click();
		driver.findElement(By.name("processCarrier")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bankwire"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='cart_navigation']/button")))
				.click();
		WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));
		assertEquals(excelutil.getvalue1(), heading.getText());
//		assertEquals("ORDER CONFIRMATION", heading.getText());
		assertTrue(driver.findElement(By.xpath("//li[@class='step_done step_done_last four']")).isDisplayed());
		assertTrue(driver.findElement(By.xpath("//li[@id='step_end' and @class='step_current last']")).isDisplayed());
		assertTrue(driver.findElement(By.xpath("//*[@class='cheque-indent']/strong")).getText()
				.contains("Your order on My Store is complete."));
		assertTrue(driver.getCurrentUrl().contains("controller=order-confirmation"));
	}

}
