/*
 * 
 * Author - Parikshit
 * 
 * 
 */

package com.ndtv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import common.utils.CommUtils;

public class BaseNDTV {

	public static WebDriver driver;
	public static Properties prop;  //to get the Environmental Properties like Browser and URLs


	
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

		driver.manage().timeouts().pageLoadTimeout(CommUtils.Page_LoadOut_Time, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(CommUtils.Implicitely_Wait, TimeUnit.SECONDS);

		driver.manage().window().maximize();
		driver.get(prop.getProperty("url"));

	}
	
	
	
	//@AfterMethod   //Closing browser and clearing cookies
	public void houseKeeping() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

}
