package org.openweathermap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.utils.CityNameGenerator;
import groovyjarjarantlr4.v4.runtime.atn.SemanticContext.AND;

import java.net.URI;
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
	
	
	@Test(dataProvider = "getCities")
	public void testOpenWeatherMapData(String City) {
		RestAssured.baseURI = prop.getProperty("baseURI");
		String key = prop.getProperty("APIkey");
		//System.out.println(prop.getProperty("baseURI"));
		//System.out.println(APIkey);
	
		Response response = given().log().all()
							.queryParam("q", City)
							.queryParam("appid", Session_ID)
							.queryParam("units", "metric")
							.when()
							.get("/data/2.5/weather") //I could have saved this elsewhere and brought here but we are going to use only one api here
							.then()
							.assertThat().statusCode(200) //Is response okay? , lets go ahead with data pulling
							.extract()
							.response();
		
		System.out.println(response.asString());
						
	}
	
	@DataProvider //will return cities names as String one by one
	public Iterator<String> getCities() {
		ArrayList<String> it = CityNameGenerator.storeData();
		return it.iterator();
	}
	
	public int temperatureInDegreeC(Response res) {
		JsonPath json = new JsonPath(res.asString()); //converting response to Json format to extract data
		
		return 0;
	}

}
