package org.openweathermap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;



public class BaseOpenWeatherMap {
	public  Properties prop;
	public  String Session_ID;
	public static String listOfCities = "listOfCities";
	
	@BeforeMethod
	public  void setup() throws IOException {
		prop = new Properties();
		FileInputStream inputStream = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/env/properties/config.properties");
		prop.load(inputStream);
		Session_ID = prop.getProperty("APIkey");
		//Session_ID = Payload.returnKey(); // in case the APIkey is not static and needs to be generated
												//every time, a key-generator util class "Payload"
												//and method returnKey could be		
												//created to generate a new key every time or for every test 
		
		
	}

}
