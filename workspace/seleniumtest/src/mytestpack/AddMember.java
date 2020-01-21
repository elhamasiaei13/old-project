package mytestpack;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import com.thoughtworks.selenium.Selenium;

public class AddMember {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/home/dev-03/Desktop/chromedriver");
		driver = new ChromeDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	@Test
	public void testAddMember() throws Exception {
		driver.manage().window().maximize();
		driver.get(baseUrl + "/issuemanager-web/login");
		driver.findElement(By.name("username")).sendKeys("zammad");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText("مشاهده اعضا")).click();
		driver.findElement(By.linkText("افزودن")).click();
		driver.findElement(By.id("firstName")).clear();
		driver.findElement(By.id("firstName")).sendKeys("akram");
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys("musavi");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("aki");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("am_1523@yahoo.com");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("789");
		driver.findElement(By.id("input-21")).clear();
		driver.findElement(By.id("input-21")).sendKeys("/home/dev-03/Downloads/ui - jira -sample 01.png");
		driver.findElement(By.cssSelector("form.form-horizontal > button.btn.btn-default")).click();

		driver.findElement(By.cssSelector("form.form-horizontal > button.btn.btn-default")).click();
		
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
