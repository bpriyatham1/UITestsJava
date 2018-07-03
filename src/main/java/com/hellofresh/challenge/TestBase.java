package com.hellofresh.challenge;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.hellofresh.utilities.Utils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
	public static WebDriver driver;
	public static WebDriverWait wait;
	static Properties prop;
	public ExtentReports extent;
	public ExtentTest extentTest;

	Logger log = Logger.getLogger(TestBase.class);
	
	String os = System.getProperty("os.name").toLowerCase();
	//To validate 32bit or 64 bit Operating System
	String dataModel =  System.getProperty("os.arch").toLowerCase();
	
	protected String existingUserEmail = "hf_challenge_123456@hf12345.com";
	protected String existingUserPassword = "12345678";

	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("src/main/java/com/hellofresh/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeTest
	public void SetExtent() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport.html", true);
		extent.addSystemInfo("HostName", "Lenovo ThinkPad Windows 10");
		extent.addSystemInfo("User Name", "Priyatham Bolli");
		extent.addSystemInfo("OS", "Windows 10");
	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@Parameters("browser")
	@BeforeMethod
	public void setUp(String browser) {

		PropertyConfigurator.configure("Log4j.properties");
		try {
			log.debug("This is debug message");
			log.info("This is info message");
			log.warn("This is warn message");
			log.fatal("This is fatal message");
			log.error("This is error message");
			System.out.println("All Level Logs are printed successfully....");
		} catch (Exception e) {
			System.out.println("Failed to print Logs" + e.getStackTrace());
		}

		log.info("Launching Browser");
		
		if (browser.equalsIgnoreCase("firefox") && os.equals("windows 10")) {
			System.setProperty("webdriver.gecko.driver", "src/main/java/com/hellofresh/drivers/geckodriver.exe");
			driver = new FirefoxDriver();
		} 
		else if (browser.equalsIgnoreCase("firefox") && os.contains("linux")  && dataModel.contains("64")) {
			System.setProperty("webdriver.gecko.driver", "src/main/java/com/hellofresh/drivers/geckodriver_linux64");
			driver = new FirefoxDriver();
		}
		else if (browser.equalsIgnoreCase("firefox") && os.contains("linux")  && dataModel.contains("32")) {
			System.setProperty("webdriver.gecko.driver", "src/main/java/com/hellofresh/drivers/geckodriver_linux32");
			driver = new FirefoxDriver();
		}
		else if (browser.equalsIgnoreCase("firefox") && os.contains("mac")) {
			System.setProperty("webdriver.gecko.driver", "src/main/java/com/hellofresh/drivers/geckodriver_mac");
			driver = new FirefoxDriver();
		}
		else if (browser.equalsIgnoreCase("chrome") && os.equals("windows 10")) {
			System.setProperty("webdriver.chrome.driver", "src/main/java/com/hellofresh/drivers/chromedriver_win.exe");
			driver = new ChromeDriver();
		}
		else if (browser.equalsIgnoreCase("chrome") && os.contains("linux")) {
			System.setProperty("webdriver.chrome.driver", "src/main/java/com/hellofresh/drivers/chromedriver_linux");
			driver = new ChromeDriver();
		}
		else if (browser.equalsIgnoreCase("chrome") && os.contains("mac")) {
			System.setProperty("webdriver.chrome.driver", "src/main/java/com/hellofresh/drivers/chromedriver_mac");
			driver = new ChromeDriver();
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 10, 50);
		driver.manage().timeouts().pageLoadTimeout(Utils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Utils.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
		log.info("Launching Application in Chrome Browser");
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, "Test Case Failed is: " + result.getName());// to add name in extent report
			extentTest.log(LogStatus.FAIL, "Test Case Failed is: " + result.getThrowable());// to add error/exception in
																							// extent report

			String screenshotPath = TestBase.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); // Add screenshot in extent
																							// report
			// extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath)); //
			// Add screenCast in extent report
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, "Test Case Skipped is: " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, "Test Case Passed is: " + result.getName());
			extentTest.log(LogStatus.INFO, "Ending the Execution of the method: " + "'" + result.getName() + "'");
		}
		extent.endTest(extentTest);// end test and create HTML Report

		driver.close();
		driver.quit();
	}

}
