package mytestpack;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



public class AddTask {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/home/dev-03/Desktop/chromedriver");
		driver = new ChromeDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(baseUrl + "/issuemanager-web/login");
		driver.findElement(By.name("username")).sendKeys("zammad");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Test
	public void testIM() throws Exception {

		driver.findElement(By.linkText("مشاهده کارهای ایجاد شده")).click();
		driver.findElement(By.cssSelector("i.fa.fa-plus")).click();
		driver.findElement(By.cssSelector(".select2-selection__arrow")).click();
		Thread.sleep(1000);
		driver.findElement(By.cssSelector(".select2-results__option.select2-results__option--highlighted")).click();		
		Select dropdown2 = new Select(driver.findElement(By.cssSelector(".js-example-basic-hide-search.form-control")));
		dropdown2.selectByIndex(1);
		Select dropdown3 = new Select(driver.findElement(By.id("priorityId")));
		dropdown3.selectByIndex(1);
		driver.findElement(By.id("subject")).clear();
		driver.findElement(By.id("subject")).sendKeys("عنوان");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@After
	public void tearDown() throws Exception {
		// driver.quit();
		// String verificationErrorString = verificationErrors.toString();
		// if (!"".equals(verificationErrorString)) {
		// fail(verificationErrorString);
		// }
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
