/**
 * References:
 * https://selenium99.com/ssl-certificate-error-handling-selenium/
 * https://www.browserstack.com/guide/selenium-with-java-for-automated-test
 */

/*
 * TO DO:
 * -change to explicit waits where applicable
 * -reduce redundant code?
 * -add cross browser testing
 * -change to testNG
 * -add assertions
 * -look into testNG reporting
 */

package scripts;

import java.io.File;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import com.google.common.io.Files;

public class functionTest {

	public static void main(String[] args) {
		String message;

//site currently does not have SSL,so need to handle untrsuted SSL cert

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		ChromeOptions options = new ChromeOptions();
		options.merge(capabilities);

//setting the driver executable
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");

//Initiating chromedriver
		WebDriver driver = new ChromeDriver(options);

//Applied wait time
		// WebDriverWait wait = new WebDriverWait(driver,100);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//maximize window
		driver.manage().window().maximize();

//open browser with desired URL
		driver.get("https://www.marthaczerwik.com");
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		message = "1 - Landing Page";
		saveScreenshot(driver, message);

//open login page
		WebElement loginPage = driver.findElement(By.linkText("Log In"));
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

		loginPage.click();

//log in - test blank fields

		WebElement username = driver.findElement(By.name("username"));
		username.sendKeys("");
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("");
		WebElement submit = driver.findElement(By.className("loginButtons"));
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		message = "2 - Blank Fields Test";
		submit.click();
		saveScreenshot(driver, message);
//log in - test non existant credentials
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

		username = driver.findElement(By.name("username"));
		username.sendKeys("username");
		password = driver.findElement(By.name("password"));
		password.sendKeys("password");
		submit = driver.findElement(By.className("loginButtons"));
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		message = "3 - Incorrect Credentials Test";
		saveScreenshot(driver, message);

		submit.click();
//log in - test  existant credentials
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

		username = driver.findElement(By.name("username"));
		username.sendKeys("root");
		password = driver.findElement(By.name("password"));
		password.sendKeys("1234");
		submit = driver.findElement(By.className("loginButtons"));
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		message = "4 - Correct Credentials Test";
		saveScreenshot(driver,  message);

		submit.click();
//log out
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		WebElement userPage = driver.findElement(By.id("profileLink"));
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		userPage.click();
		WebElement logout = driver.findElement(By.id("logout"));
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		message = "5 - Logout located";
		saveScreenshot(driver,  message);

		logout.click();
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		message = "6 - Logged Out Test";
		saveScreenshot(driver, message);

//chatbot
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		WebElement chatbot = driver.findElement(By.className("open-button"));
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		chatbot.click();
		message = "7 - Chatbot opened";
		saveScreenshot(driver,  message);
		
		chatbot = driver.findElement(By.id("user_input"));
		chatbot.sendKeys("hi");
		chatbot = driver.findElement(By.id("submit"));
		chatbot.click();
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		message = "8 - Chatbot reply";
		saveScreenshot(driver,  message);
		
		chatbot = driver.findElement(By.id("user_input"));
		chatbot.sendKeys("i cant login");
		chatbot = driver.findElement(By.id("submit"));
		chatbot.click();
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		message = "9 - Chatbot reply 2";
		saveScreenshot(driver,  message);
		

//closing the browser
		driver.close();

	}

//method to take screenshot
	public static void saveScreenshot(WebDriver driver, String message) {
		String pathFile = "C:\\Users\\marth\\Desktop\\JSFR TEST\\";
		DateFormat df = new SimpleDateFormat("dd-MM-yy");
		Date dateobj = new Date();

		// convert web driver object to takescreenshot object
		TakesScreenshot screenshot = ((TakesScreenshot) driver);

		// Call getScreenshotAs method to create image file
		File SrcFile = screenshot.getScreenshotAs(OutputType.FILE);

		// Move image file to new destination
		File DestFile = new File(pathFile + message + " " + df.format(dateobj) + ".jpg");

		// Copy file at destination
		try {
			Files.copy(SrcFile, DestFile); // change to
		} catch (IOException e) {
			System.out.println("Unable to take screenshot");
			e.printStackTrace();
		}
	}

}
