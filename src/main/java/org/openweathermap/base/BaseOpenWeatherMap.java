package org.openweathermap.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeMethod;



public class BaseOpenWeatherMap {
	public  Properties prop;
	public  String Session_ID;
	
	@BeforeMethod
	public  void setup() throws IOException {
		prop = new Properties();
		File file;
		FileInputStream inputStream = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\files\\config\\env.properties");
		prop.load(inputStream);
		Session_ID = prop.getProperty("APIkey");
		//Session_ID = Payload.session_ID(prop);
	}

}
