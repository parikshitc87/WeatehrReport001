package org.openweathermap.testcases;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openweathermap.BaseOpenWeatherMap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.utils.CityNameGenerator;
import common.utils.EnterAllData;
import common.utils.Xls_Reader;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext.AND;

import java.io.FileInputStream;
import java.math.RoundingMode;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;
public class WeatherDataOWM extends BaseOpenWeatherMap{
	
	double liveTemp;
	double maxTemp;
	int humidity;
	double windSpeed;
	String weatherCondition;
	JsonPath json;
	DecimalFormat df = new DecimalFormat("0.00");
	static Workbook book;
	static Sheet sheet;
	Xls_Reader xlsReader;
	FileInputStream file;
	int columnCount;
	int dataEntryFlag;
	
	
	
	
	@Test(dataProvider = "getCities") 
	public void testOpenWeatherMapData(String City) { //Extracts response from OpenWeatherMap.org, extracts weather data, stores it in Excel 
		RestAssured.baseURI = prop.getProperty("baseURI");
		String key = prop.getProperty("APIkey");
		//System.out.println(prop.getProperty("baseURI"));
		//System.out.println(APIkey);
	
		Response response = given().log().all() 
							.queryParam("q", City)
							.queryParam("appid", key)
							.queryParam("units", "metric")
							.when()
							.get("/data/2.5/weather") //I could have saved this elsewhere and brought here but we are going to use only one API
							.then()
							.assertThat().statusCode(200) //Is response okay? , lets go ahead with data pulling
							.extract()
							.response();
		
		ArrayList<Object> cityWeatherData = new ArrayList<Object>();
		dataEntryFlag = CityNameGenerator.reader.getRowCount("listOfCities"); 
		
		System.out.println(response.asString());
		df.setRoundingMode(RoundingMode.UP);

		liveTemp = ((Number) currentTemperature(response)).doubleValue();
		cityWeatherData.add(df.format(liveTemp));
		
		/*
		 * maxTemp = ((Number) maxTemperature(response)).doubleValue();
		 * cityWeatherData.add(df.format(maxTemp));
		 */
		
		humidity = currentHumidity(response);
		cityWeatherData.add(humidity);
		
		/*
		 * windSpeed = ((Number)windCondition(response)).doubleValue();
		 * cityWeatherData.add(df.format(windSpeed));
		 */
		
		weatherCondition = weatherCondition(response);
		cityWeatherData.add(weatherCondition);
		
		System.out.println("Data for: Temp | Humidity (%)  | Weather Conditions");

		for(Object ob : cityWeatherData) {
			System.out.print("| ["+String.valueOf(ob)+"] |");
		}
		System.out.println();
		
		EnterAllData.enterOpenWeatherMapData(cityWeatherData, listOfCities, City); //Saves test data received from API in Excel sheet


		
	}
	
	@DataProvider //will return cities names as String one by one
	public Iterator<String> getCities() {
		ArrayList<String> it = CityNameGenerator.storeData();
		return it.iterator();
	}
	
	
	
	//Extracting Live temp in C/F 
	Object currentTemperature(Response res) {
		 json = new JsonPath(res.asString()); //converting response to Json format to extract data
		System.out.println("Tempearature is " + json.get("main.temp") + prop.getProperty("unit"));
		return  json.get("main.temp");
	 
	}
	//Extracting Max Temp in C
	Object maxTemperature(Response res) {
		json = new JsonPath(res.asString());
		System.out.println("Max Tempearature is " + json.get("main.temp") + prop.getProperty("unit"));
		return  json.get("main.temp_max");
	}
	
	//Extracting Current Humidity
	Integer currentHumidity(Response res) {
		json = new JsonPath(res.asString());
		System.out.println("Current Humidity is " + json.get("main.humidity") + "%");
		return json.get("main.humidity");
	}
	
	//Extracting wind Speed m/s
	Object windCondition(Response res) {
		json = new JsonPath(res.asString());
		System.out.println("Current wind speed is " + json.get("wind.speed") + "m/s");
		return json.get("wind.speed");
	}
	
	//Extracting Weather conditions
	String weatherCondition(Response res) {
		json = new JsonPath(res.asString());
		System.out.println("Current Weather condition is " + json.get("weather[0].main") + " & " + json.get("weather[0].description"));
		return json.get("weather[0].main") + " and " + json.get("weather[0].description");
	}
	
	
	
}
