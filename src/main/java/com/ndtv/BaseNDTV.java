/*
 * 
 * Author - Parikshit
 * 
 * 
 */

package com.ndtv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.xml.LaunchSuite;

import common.utils.CommUtils;


import org.apache.xmlbeans.impl.xb.xsdschema.Public;

public class BaseNDTV {

	public static WebDriver driver;
	public static Properties prop;  //to get the Environmental Properties like Browser and URLs


	@BeforeMethod
	public void Setup() {
		
		
		prop = new Properties();
		try {
			FileInputStream ipStream = new FileInputStream(System.getProperty("user.dir")+ "src\\main\\java\\env\\properties\\config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\main\\resources\\com\\drivers\\chromedriver.exe");

		driver = new ChromeDriver();

		driver.manage().timeouts().pageLoadTimeout(CommUtils.Page_LoadOut_Time, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(CommUtils.Implicitely_Wait, TimeUnit.SECONDS);

		driver.manage().window().maximize();
		driver.get(prop.getProperty("browser"));

	}
	
	
	
	@AfterMethod
	public void houseKeeping() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

}
