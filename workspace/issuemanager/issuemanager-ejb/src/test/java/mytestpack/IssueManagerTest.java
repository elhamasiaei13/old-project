package mytestpack;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.util.DateUtil;
import com.parvanpajooh.commons.util.LocaleUtil;

public class IssueManagerTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private static final String REPO_PATH = "/usr/local/issuemanager/seleniumTest/";

	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;

	// public AddMember(String firstName, String lastName, String username,
	// String email, String password) {
	//
	// this.firstName = firstName;
	// this.lastName = lastName;
	// this.username = username;
	// this.email = email;
	// this.password = password;
	// }

	@Before
	public void setUp() throws Exception {
//		System.setProperty("webdriver.chrome.driver", "/home/dev-03/Desktop/chromedriver");
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// login
		driver.manage().window().maximize();
		driver.get(baseUrl + "/issuemanager-web/login");
		driver.findElement(By.name("username")).sendKeys("zammad");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	public static UserInfo getGuestUserInfo() {
		Long userId = 0L;
		String userName = "anonymousUser";
		String firstName = "guest";
		String lastName = "guest";
		Set<String> roleNames = new HashSet<String>();
		String timeZone = ZoneId.systemDefault().toString();
		String calendar = DateUtil.CALENDAR_GREGORIAN;
		String ip = null;
		String contextPath = null;
		String tenantId = null;
		String locale = LocaleUtil.fromLanguageId("fa").toString();

		return new UserInfo(userId, userName, firstName, lastName, ip, contextPath, tenantId, roleNames, locale, timeZone, calendar) {
		};
	}

	@Test
	public void testAddMember() throws Exception {

		// add Group
		driver.findElement(By.linkText("مشاهده گروه‌ها")).click();
		driver.findElement(By.linkText("افزودن")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("گروه تستی4");
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("توضیح گروه تستی4");
		driver.findElement(By.cssSelector("button.btn.btn-default")).click();

		// read Member data from excel
		UserInfo userInfo = getGuestUserInfo();
		InputStream ExcelFileToRead = new FileInputStream(REPO_PATH + "test.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();

			// add Member
			driver.findElement(By.linkText("مشاهده اعضا")).click();
			driver.findElement(By.linkText("افزودن")).click();
			driver.findElement(By.id("firstName")).clear();
			driver.findElement(By.id("firstName")).sendKeys(row.getCell(0).toString());
			driver.findElement(By.id("lastName")).clear();
			driver.findElement(By.id("lastName")).sendKeys(row.getCell(1).toString());
			Thread.sleep(1000);
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys(row.getCell(2).toString());
			driver.findElement(By.id("email")).clear();
			driver.findElement(By.id("email")).sendKeys(row.getCell(3).toString());
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys(row.getCell(4).toString());
			driver.findElement(By.id("input-21")).clear();
			driver.findElement(By.id("input-21")).sendKeys("/home/dev-03/Downloads/ui - jira -sample 01.png");
			Thread.sleep(1000);
			driver.findElement(By.cssSelector("form.form-horizontal > button.btn.btn-default")).click();
			Thread.sleep(1000);

			// add Member to Group
			driver.findElement(By.linkText("مشاهده گروه‌ها")).click();
			driver.findElement(By.xpath("//table[@id='GroupTable']/tbody/tr[last()]/td[2]/div/a[3]/i")).click();
			driver.findElement(By.xpath("//table[@id='MembershipTable']/tbody/tr[last()]/td[5]/div/ins")).click();
			Thread.sleep(1000);

			driver.findElement(By.cssSelector("button.btn.btn-default")).click();

			// logout
			driver.findElement(By.cssSelector("span.glyphicon.glyphicon-off")).click();

			// login with created Member
			driver.get(baseUrl + "/issuemanager-web/login");
			driver.findElement(By.name("username")).sendKeys(row.getCell(2).toString());
			driver.findElement(By.name("password")).sendKeys(row.getCell(4).toString());
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button[@type='submit']")).click();

			// add Task
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
			driver.findElement(By.id("subject")).sendKeys(" عنوان تسک");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button[@type='submit']")).click();

			// logout
			driver.findElement(By.cssSelector("span.glyphicon.glyphicon-off")).click();
			
			//login as Admin
			driver.get(baseUrl + "/issuemanager-web/login");
			driver.findElement(By.name("username")).sendKeys("zammad");
			driver.findElement(By.name("password")).sendKeys("123");
			driver.findElement(By.xpath("//button[@type='submit']")).click();			
		}
	}

	@After
	public void tearDown() throws Exception {
//		 driver.quit();
//		 String verificationErrorString = verificationErrors.toString();
//		 if (!"".equals(verificationErrorString)) {
//		 fail(verificationErrorString);
//		 }
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
