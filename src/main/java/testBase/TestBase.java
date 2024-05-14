package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import enums.Browsers;
import enums.OS;
import excelReader.ExcelReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import samlUtility.TestUtility;

public class TestBase {

	public static WebDriver driver;
	public static Properties Repository = new Properties();
	public static Properties Config = new Properties();
	public static Properties AppText = new Properties();
	public static ExcelReader SuiteData;
	public static ExcelReader TestStepData;

	public static String keyword;
	public static String webElement;
	public static String TestDataField;
	public static String TestData;
	public static String ProceedOnFail;
	public static String TSID;
	public static String Description;
	public static File f ;
	public static FileInputStream FI;

	public static void Initialize() throws IOException {

		TestStepData = new ExcelReader(System.getProperty("user.dir")+"/src/test/java/testData/TestSuite1Data.xlsx");
		SuiteData = new ExcelReader(System.getProperty("user.dir")+"/src/test/java/testData/TestSuite1.xlsx");

		f = new File(System.getProperty("user.dir")+"/src/test/java/propertiesFiles/config.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Login_UserInfo.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Carrier_Product.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Plan.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Owner.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Contract_Features.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Beneficiary.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Payment.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Payment_Suitability.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Payment_Additional_Info.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Investment_Professional_Information.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Transaction_Suitability.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/Additional_Client_Questions.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/EbixLogin.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);

		f = new File(System.getProperty("user.dir")+"/src/test/java/repository/JointAnnuitant.properties");
		FI = new FileInputStream(f);
		Repository.load(FI);
	}

	public static String now(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fm = new SimpleDateFormat();
		return fm.format(cal.getTime());
	}



	public static WebDriver selectBrowser(String browser) {
		if (System.getProperty("os.name").toLowerCase().contains(OS.WINDOW.name().toLowerCase())) {
			if (browser.equalsIgnoreCase(Browsers.CHROME.name())) {
				//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
				options.addArguments("--start-maximized");
				options.addArguments("--disable-web-security");
				options.addArguments("--no-proxy-server");

				Map<String, Object> prefs = new HashMap<>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);

				options.setExperimentalOption("prefs", prefs);
				driver = new ChromeDriver(options);
				driver.manage().window().maximize();
			//	driver.manage().timeouts().implicitlyWait(Integer.parseInt(Wait.getImplicitWait()), TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(Wait.getImplicitWait())));
			} else if (browser.equalsIgnoreCase(Browsers.FIREFOX.name())) {
				//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/geckodriver.exe");
				//driver = new FirefoxDriver();
				WebDriverManager.firefoxdriver().setup();
				driver.manage().window().maximize();
			//	driver.manage().timeouts().implicitlyWait(Integer.parseInt(Wait.getImplicitWait()), TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(Wait.getImplicitWait())));
			}
			else if (browser.equalsIgnoreCase(Browsers.IE.name())) {
				//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/geckodriver.exe");
				//driver = new FirefoxDriver();
				WebDriverManager.iedriver().setup();
				driver.manage().window().maximize();
		//		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Wait.getImplicitWait()), TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(Wait.getImplicitWait())));
			}
			else if (browser.equalsIgnoreCase(Browsers.EDGE.name())) {
				//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/geckodriver.exe");
				//driver = new FirefoxDriver();
				WebDriverManager.edgedriver().setup();
				driver.manage().window().maximize();
		//		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Wait.getImplicitWait()), TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(Wait.getImplicitWait())));
			}
		} else if (System.getProperty("os.name").toLowerCase().contains(OS.MAC.name().toLowerCase())) {
			if (browser.equalsIgnoreCase(Browsers.CHROME.name())) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver");
				driver = new ChromeDriver();
		//		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Wait.getImplicitWait()), TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(Wait.getImplicitWait())));
			} else if (browser.equalsIgnoreCase(Browsers.FIREFOX.name())) {
				System.setProperty("webdriver.firefox.marionette", System.getProperty("user.dir") + "/src/test/resources/drivers/geckodriver");
				driver = new FirefoxDriver();
		//		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Wait.getImplicitWait()), TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(Wait.getImplicitWait())));
			}
		}
		return driver;
	}

	 static String reportDirectery = System.getProperty("user.dir")+"/src/test/java/screenshot";

		public static String getScreenShot(String fileName){
			if(driver == null){
				return null;
			}
			if(fileName==""){
				fileName = "blank";
			}
			Reporter.log("captureScreen method called");
			File destFile = null;
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			File screFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try{
				destFile = new File(reportDirectery+"/"+fileName+"_"+formater.format(calendar.getTime())+".png");
				Files.copy(screFile.toPath(), destFile.toPath());
				Reporter.log("<a href='"+destFile.getAbsolutePath()+"'><img src='"+destFile.getAbsolutePath()+"'height='100' width='100'/></a>");
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return destFile.toString();
		}


		@BeforeSuite
		public void setLog4j()
		{
			TestUtility.setDateForLog4j();
		}

	public static void main(String[] args) throws IOException {
		TestBase.Initialize();
		System.out.println(Repository.getProperty("login.signIn"));

		System.out.println(SuiteData.getRowCount("TC03"));

	}

}
