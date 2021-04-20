package com.datadriven.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.excel.utility.UtilityExcel;

import io.github.bonigarcia.wdm.WebDriverManager;

public class KekaLoginPageAutomate {

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(dataProvider = "getData")
	public void loginPage(String username, String password, String expected) throws InterruptedException {

		driver.get("https://app.keka.com/account/login?returnUrl=%2F");
		WebElement usertext = driver.findElement(By.id("email"));
		usertext.clear();
		usertext.sendKeys(username);
		driver.findElement(By.xpath("//button[text()='Login']")).click();

		WebElement pwdtext = driver.findElement(By.id("password"));
		pwdtext.clear();
		pwdtext.sendKeys(password);

		driver.findElement(By.xpath("//button[text()='Login']")).click();

		if ("valid".equals(expected)) {

			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.titleIs("Home | Dashboard"));
			WebElement element = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a#userProfile div.profile-picture")));
			element.click();
			driver.findElement(By.cssSelector("form#logoutForm a")).click();

		} else {
			String actualtext = driver.findElement(By.cssSelector("div.login-form li")).getText();
			Assert.assertTrue("Invalid login attempt.".equals(actualtext), "Login successfull for invalid credential");
		}

	}

	@DataProvider(name = "getData")
	public String[][] getData() throws IOException {
		
	    String homepath =System.getProperty("user.dir");
		String path = homepath+"\\src\\test\\java\\com\\testdata\\KekaLoginTD.xlsx";

//		String path ="C:\\Windows\\System32\\config\\systemprofile\\eclipse-workspace\\DataDriven\\src\\test\\java\\com\\testdata\\Keka TestData.xlsx";
		UtilityExcel xlutil = new UtilityExcel(path);
		int totalrows = xlutil.getRowCount("sheet1");
		int totalcols = xlutil.getCellCount("sheet1", 1);
		
		String loginData[][] = new String[totalrows][totalcols];
		
		for(int i =1;i<=totalrows;i++)
		{
			for(int j=0;j<totalcols;j++)
			{
			loginData[i-1][j] = xlutil.getCellData("sheet1", i, j);
			}
		}
		
		return loginData;
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
