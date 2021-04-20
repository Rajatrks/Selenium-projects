package com.keka.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends BasePage {
	
	@FindBy(xpath=" //a[text()=' Clairvoyant India Private Limited ']")
	WebElement textlabel;

	@FindBy(xpath="//input[@placeholder='Search']")
	WebElement serachbox;
	
	@FindBy(xpath=" //a[@placement='right']")
	WebElement inputBox;


	//Initializing the page factory or Object Repository
			public HomePage(WebDriver driver)
			{
				super(driver);
			}
			
			public String validateHomePageTitle() {
				return driver.getTitle();
			}
			
			public boolean validateTextLabel() {
				return textlabel.isDisplayed();
			}

			public EmployeePage clickOnSearchBox(String empName) {
				serachbox.clear();
				serachbox.sendKeys(empName);
				inputBox.click();
				return new EmployeePage();
			}

}
