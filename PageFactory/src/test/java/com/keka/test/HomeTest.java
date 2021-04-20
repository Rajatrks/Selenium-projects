package com.keka.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.test.TestBase;
import com.keka.pages.EmployeePage;
import com.keka.pages.HomePage;
import com.keka.pages.LoginPage;

public class HomeTest extends TestBase {

	LoginPage login;
	HomeTest hometest; // only to define super() of parent class
	HomePage homepage;
	EmployeePage employee;

	public HomeTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		hometest = new HomeTest();
		driverInitialization("chrome");
	}

	@Test(priority = 1)
	 public void verifyHomePageTitle() throws IOException {
		driver.get(getUrl());
		login = new LoginPage(driver);
		login.clickOnLogin(property.getProperty("username"));
		homepage = login.loginToKeka(property.getProperty("password"));
		homepage = new HomePage(driver);
		homepage.explicitlyWait(driver,"Home | Dashboard", 5);
		Assert.assertEquals("Home | Dashboard", homepage.validateHomePageTitle());
	}
	
	@Test(priority = 2)
	 public void verifyTextLabel() throws IOException {
		driver.get(getUrl());
		login = new LoginPage(driver);
		login.clickOnLogin(property.getProperty("username"));
		homepage = login.loginToKeka(property.getProperty("password"));
		homepage = new HomePage(driver);
		Assert.assertTrue(homepage.validateTextLabel(), "Clairvoyant India Private Limited not found");
	}

	@Test(priority = 3)
	public void cilckOnSearchBox() {
		driver.get(getUrl());
		login = new LoginPage(driver);
		login.clickOnLogin(property.getProperty("username"));
		homepage = login.loginToKeka(property.getProperty("password"));
		homepage = new HomePage(driver);
		employee = homepage.clickOnSearchBox("Rajat");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
