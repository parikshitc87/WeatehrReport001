//  Author - Parikshit

package com.ndtv.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import common.utils.CityNameGenerator;
import common.utils.CommUtils;
import logger.WebEvent.*;

public class BaseNDTV {
	
	public static WebDriver driver;
	public static Properties prop;  //to get the Environmental Properties like Browser and URLs
	public static String listOfCities = "listOfCities";
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;



	
	public void Setup() {
		
		
		prop = new Properties();
		try {
			FileInputStream ipStream = new FileInputStream(System.getProperty("user.dir")
					+ "/src/main/java/env/properties/config.properties");
			prop.load(ipStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		

		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\main\\resources\\drivers\\chromedriver.exe");

		driver = new ChromeDriver();
		
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().timeouts().pageLoadTimeout(CommUtils.Page_LoadOut_Time, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(CommUtils.Implicitely_Wait, TimeUnit.SECONDS);

		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));

	}
	
	public static void sendKeys(WebDriver driver, WebElement element, int timeout, String City) {
		new WebDriverWait(driver, 	timeout).until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(City);
	}
	
	public static void clickOn(WebDriver driver, WebElement element, int timeout) {
		new WebDriverWait(driver, timeout)
		.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
		}
		
	
	public static String APIkey() {
		return prop.getProperty("APIkey");
	}
	
	@AfterMethod   //Closing browser and clearing cookies
	public void houseKeeping() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	


}
