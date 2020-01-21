package mytestpack;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyTestClass {
	
	

	public static void main(String[] args) {
        // declaration and instantiation of objects/variables
//        WebDriver driver = new FirefoxDriver();
		
		System.setProperty("webdriver.chrome.driver", "/home/dev-03/Desktop/chromedriver");
        WebDriver driver = new ChromeDriver();
        String baseUrl = "http://newtours.demoaut.com";
        String expectedTitle = "Welcome: Mercury Tours";
        String actualTitle = "";

        
//        WebDriver driver = new RemoteWebDriver("http://localhost:9515", DesiredCapabilities.chrome());
//        driver.get("http://www.google.com");
        // launch Firefox and direct it to the Base URL
        driver.get(baseUrl);

        // get the actual value of the title
        actualTitle = driver.getTitle();

        /*
         * compare the actual title of the page witht the expected one and print
         * the result as "Passed" or "Failed"
         */
        if (actualTitle.contentEquals(expectedTitle)){
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
        }
       
        //close Firefox
//        driver.close();
       
        // exit the program explicitly
        System.exit(0);
    }

}
