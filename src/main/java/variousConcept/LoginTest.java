package variousConcept;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

	String browser = null;
	String url;
	WebDriver driver;

	// storing element by type
	By USERNAME_FIELD = By.xpath("//input[@id='username']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By SIGNIN_FIELD = By.xpath("//BUTTON[@name='login']");
	By DASHBOARD_HEADER_FIELD = By.xpath("//h2[contains(text(),'Dashboard')]");
	By MENU_NAV_FIELD = By.xpath("//*[@id=\"page-wrapper\"]/div[1]/nav/div/a");
	By CUSTOMER_MENU_FIELD = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By NEW_CUSTOMER_MENU_FIELD = By.xpath("//a[text()='Add Customer']");
	By ADD_CONTACT_HEADER_FIELD = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");

	@BeforeClass
	public void readConfig() {
		// How to read properties?
		// fileReader //bufferReader// inputStream// Scanner [need one of these
		// class to read the file]

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("browser used: " + browser);
			url = prop.getProperty("url");

		} catch (IOException e) {

		}
	}

	@BeforeMethod
	public void inni() {
//		if (browser.equalsIgnoreCase("chrome")) {
//			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
//			driver = new ChromeDriver();
//		} else if (browser.equalsIgnoreCase("edge")) {
//			System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
//			driver = new EdgeDriver();
//		}

		System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
		driver = new ChromeDriver();
		// System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
		// driver = new EdgeDriver();

		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void loginTest() {
		driver.findElement(USERNAME_FIELD).sendKeys("demo@techfios.com");
		driver.findElement(PASSWORD_FIELD).sendKeys("abc123");
		driver.findElement(SIGNIN_FIELD).click();
		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), "Dashboard",
				"Dashboard page not found");

	}

	@Test
	public void addContact() throws InterruptedException {
		loginTest();
		driver.findElement(MENU_NAV_FIELD).click();
		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(NEW_CUSTOMER_MENU_FIELD).click();
		 Assert.assertEquals(driver.findElement(ADD_CONTACT_HEADER_FIELD).getText(),
		 "Add Contact", "Add Contact");
	

	}

	@AfterMethod
	public void tearDown() {
		driver.close();

	}

}
