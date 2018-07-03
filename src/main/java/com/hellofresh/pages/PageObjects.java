package com.hellofresh.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.hellofresh.challenge.TestBase;
import com.hellofresh.utilities.Utils;

public class PageObjects extends TestBase {

	WebDriver driver;

	public PageObjects(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.XPATH, using = "//a[@class='login']")
	public WebElement signInButton;

	@FindBy(how = How.ID, using = "email_create")
	public WebElement EmailaddressField;

	@FindBy(how = How.ID, using = "SubmitCreate")
	public WebElement CreateanAccountButton;

	@FindBy(how = How.ID, using = "id_gender2")
	public WebElement SelectMrsGender;

	@FindBy(how = How.ID, using = "customer_firstname")
	public WebElement FirstnameField;

	@FindBy(how = How.ID, using = "customer_lastname")
	public WebElement LastnameField;

	@FindBy(how = How.ID, using = "passwd")
	public WebElement PasswordField;

	@FindBy(how = How.ID, using = "days")
	public WebElement SelectDaysinDOB;

	@FindBy(how = How.ID, using = "months")
	public WebElement SelectMonthsinDOB;

	@FindBy(how = How.ID, using = "years")
	public WebElement SelectYearssinDOB;

	@FindBy(how = How.ID, using = "company")
	public WebElement companyField;

	@FindBy(how = How.ID, using = "address1")
	public WebElement AddressLine1Field;

	@FindBy(how = How.ID, using = "address2")
	public WebElement AddressLine2Field;

	@FindBy(how = How.ID, using = "city")
	public WebElement CityField;

	@FindBy(how = How.ID, using = "id_state")
	public WebElement selectStateField;

	@FindBy(how = How.ID, using = "postcode")
	public WebElement ZipPostalCodeField;

	@FindBy(how = How.ID, using = "other")
	public WebElement OtherInfoField;

	@FindBy(how = How.ID, using = "phone")
	public WebElement HomePhoneField;

	@FindBy(how = How.ID, using = "phone_mobile")
	public WebElement MobilePhoneField;

	@FindBy(how = How.ID, using = "alias")
	public WebElement AliasField;

	@FindBy(how = How.ID, using = "submitAccount")
	public WebElement RegisterButton;

	@FindBy(how = How.CSS, using = "h1")
	public WebElement Heading;

	@FindBy(how = How.CLASS_NAME, using = "account")
	public WebElement GetUserName;

	@FindBy(how = How.CLASS_NAME, using = "info-account")
	public WebElement WelcomeInfo;

	@FindBy(how = How.CLASS_NAME, using = "logout")
	public WebElement SignoutLink;

	 String timestamp = String.valueOf(new Date().getTime());
	 String email = "hf_challenge_" + timestamp + "@hf" + timestamp.substring(7) +
	 ".com";
	 String name = "Firstname" + Utils.generaterandomname();
	 String surname = "Lastname" + Utils.generaterandomname();
	
	 public void SignInandRegister() {
	 wait.until(ExpectedConditions.visibilityOf(signInButton)).click();
	 EmailaddressField.sendKeys(email);
	 CreateanAccountButton.click();
	 wait.until(ExpectedConditions.visibilityOf(SelectMrsGender)).click();
	 FirstnameField.sendKeys(name);
	 LastnameField.sendKeys(surname);
	 PasswordField.sendKeys("Qwerty");
	 Select select = new Select(SelectDaysinDOB);
	 select.selectByValue("1");
	 select = new Select(SelectMonthsinDOB);
	 select.selectByValue("1");
	 select = new Select(SelectYearssinDOB);
	 select.selectByValue("2000");
	 companyField.sendKeys("Company");
	 AddressLine1Field.sendKeys("Qwerty, 123");
	 AddressLine2Field.sendKeys("zxcvb");
	 CityField.sendKeys("Qwerty");
	 select = new Select(selectStateField);
	 select.selectByVisibleText("Colorado");
	 ZipPostalCodeField.sendKeys("12345");
	 OtherInfoField.sendKeys("Qwerty");
	 HomePhoneField.sendKeys("12345123123");
	 MobilePhoneField.sendKeys("12345123123");
	 AliasField.sendKeys("hf");
	 RegisterButton.click();
	 }
	
	 public void validateRegistrationprocess() {
	 WebElement heading = wait.until(ExpectedConditions.visibilityOf(Heading));
	 assertEquals(heading.getText(), "MY ACCOUNT");
	 assertEquals(driver.findElement(By.className("account")).getText(), name + " " + surname);
	 assertTrue(WelcomeInfo.getText().contains("Welcome to your account."));
	 assertTrue(SignoutLink.isDisplayed());
	 assertTrue(driver.getCurrentUrl().contains("controller=my-account"));
	 }

}
