package com.keka.test;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.base.test.TestBase;
import com.excel.util.UtilityExcel;
import com.keka.pages.HomePage;
import com.keka.pages.LoginPage;

public class LoginTest extends TestBase {

	LoginTest logintest;
	LoginPage login;
	HomePage homepage;

	public LoginTest() {
		super();
	}

	@BeforeClass
	public void setUp() {
		logintest = new LoginTest();
		driverInitialization("chrome");
	}

	@Test(priority = 1)
	public void verifyClickOnLogin() {
		driver.get(getUrl());
		login = new LoginPage(driver);
		login.clickOnLogin(property.getProperty("username"));
	}

	@Test(priority = 2)
	public void verifyTitle() {
		driver.get(getUrl());
		login = new LoginPage(driver);
		login.clickOnLogin(property.getProperty("username"));
		Assert.assertEquals("Keka Login", login.validateTitle(), "Title Mismatch");
	}

	@Test(priority = 3)
	public void verifyLogo() {
		driver.manage().deleteAllCookies();
		driver.get(getUrl());
		login = new LoginPage(driver);
		login.clickOnLogin(property.getProperty("username"));
		Assert.assertTrue(login.validateLogo(), "Logo not found");
	}

	@DataProvider(name = "getData")
	public String[][] getData() throws IOException {

		String homepath = System.getProperty("user.dir");

		String path = homepath + "\\src\\test\\resources\\com\\testdata\\KekaLoginTD.xlsx";

		UtilityExcel xlutil = new UtilityExcel(path);
		int totalrows = xlutil.getRowCount("sheet1");
		int totalcols = xlutil.getCellCount("sheet1", 1);

		String loginData[][] = new String[totalrows][totalcols];

		for (int i = 1; i <= totalrows; i++) {
			for (int j = 0; j < totalcols; j++) {
				loginData[i - 1][j] = xlutil.getCellData("sheet1", i, j);
			}
		}

		return loginData;
	}

	@Test(priority = 4, dataProvider = "getData")
	public void verifyKekaLogin(String username, String password, String expected) {
		driver.get(getUrl());
		login = new LoginPage(driver);
		login.clickOnLogin(username);
		homepage = login.loginToKeka(password);
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

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
