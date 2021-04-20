package com.keka.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage extends BasePage {
	
	@FindBy(xpath="//a[contains(text(),'login')]")
	WebElement loginBtn;
		
	@FindBy(name="Email")
	WebElement username;
	
	@FindBy(xpath="//button[@type='submit']")
	WebElement login;
	
	@FindBy(id="password")
	WebElement password;

	@FindBy(xpath="//button[text()='Login']")
	WebElement loginClick;
	
	@FindBy(className = "logo")
	WebElement logo;

	
	public LoginPage(WebDriver driver)
	{
		super(driver);
	}

	public void clickOnLogin(String user) {
		loginBtn.click();
		username.clear();
		username.sendKeys(user);
		login.click();
	}
	

	public String validateTitle() {
		return driver.getTitle();
	}
	
	public boolean validateLogo() {
		return logo.isDisplayed();
	}

	public HomePage loginToKeka(String pwd) {
		password.clear();
		password.sendKeys(pwd);
		login.click();
		return new HomePage(driver);
	}


}
