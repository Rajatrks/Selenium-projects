package com.base.test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	public static WebDriver driver;
	public Properties property;
			
		public TestBase() {
			property = new Properties();
			try {
				// Need to implement System.getProperty()
				FileInputStream fis =new FileInputStream("C:\\Users\\Rajat Sahoo\\Java Program\\KekaAutomation\\src\\main\\java\\com\\keka\\qa\\config\\config.properties");
				property.load(fis);
			}  
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		public static void driverInitialization(String browser)
		{
			if("chrome".equals(browser))
			{
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			}
			else if("firefox".equals(browser))
			{
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			else {
				System.out.println("driver initialization code for mentioned browser is not written ");
			}
			  driver.manage().window().maximize();
			  driver.manage().deleteAllCookies();
			  driver.manage().timeouts().implicitlyWait(8,TimeUnit.SECONDS);			
		}
		
		    public String getUrl() {
			String str = property.getProperty("url");
			return str;
		}

}
