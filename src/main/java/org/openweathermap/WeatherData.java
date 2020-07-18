package org.openweathermap;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.utils.CityNameGenerator;
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
public class WeatherData extends BaseOpenWeatherMap{
	
	double liveTemp;
	double maxTemp;
	int humidity;
	JsonPath json;
	ArrayList<Object> cityWeatherData = new ArrayList<Object>(3);
	DecimalFormat df = new DecimalFormat("0.00");
	static Workbook book;
	static Sheet sheet;
	Xls_Reader xlsReader;
	FileInputStream file;
	int columnCount;
	
	
	
	
	@Test(dataProvider = "getCities")
	public void testOpenWeatherMapData(String City) {
		RestAssured.baseURI = prop.getProperty("baseURI");
		String key = prop.getProperty("APIkey");
		//System.out.println(prop.getProperty("baseURI"));
		//System.out.println(APIkey);
	
		Response response = given().log().all()
							.queryParam("q", City)
							.queryParam("appid", key)
							.queryParam("units", "metric")
							.when()
							.get("/data/2.5/weather") //I could have saved this elsewhere and brought here but we are going to use only one api here
							.then()
							.assertThat().statusCode(200) //Is response okay? , lets go ahead with data pulling
							.extract()
							.response();
		
		System.out.println(response.asString());
		df.setRoundingMode(RoundingMode.UP);

		liveTemp=  ((Number) currentTemperature(response)).doubleValue();
		cityWeatherData.add(df.format(liveTemp));
		CityNameGenerator.reader.setCellData(listOfCities, "LiveTemp (OpenWeatherMap)", 2, String.valueOf(df.format(liveTemp)));
		
		maxTemp = ((Number) maxTemperature(response)).doubleValue();
		cityWeatherData.add(df.format(maxTemp));
		CityNameGenerator.reader.setCellData(listOfCities, "Max Temp (OpenWeatherMap)", 2, String.valueOf(df.format(maxTemp)));

		humidity = currentHumidity(response);
		cityWeatherData.add(humidity);
		CityNameGenerator.reader.setCellData(listOfCities, "Max Temp (OpenWeatherMap)", 2, String.valueOf(humidity));

		for(Object a : cityWeatherData) {
			System.out.println(a.toString());
		}

		System.out.println(CityNameGenerator.reader.getColumnCount("listOfCities"));
		//columnCount = CityNameGenerator.reader.getColumnCount("listOfCities");
		//addCityDataToSheet(columnCount, cityWeatherData.get(0));

		
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
	
	Object maxTemperature(Response res) {
		json = new JsonPath(res.asString());
		System.out.println("Max Tempearature is " + json.get("main.temp") + prop.getProperty("unit"));
		return  json.get("main.temp_max");
	}
	
	Integer currentHumidity(Response res) {
		json = new JsonPath(res.asString());
		System.out.println("Current Humidity is " + json.get("main.humidity") + "%");
		return json.get("main.humidity");
	}
	
	void addLiveTemp(String columnName, Object ob) {

		
	}

}
