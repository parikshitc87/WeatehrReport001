package com.weatherData.testcases;

import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ndtv.base.BaseNDTV;
import com.ndtv.pages.HomePageNDTV;
import com.ndtv.pages.WeatherPageNDTV;

import bsh.This;
import common.utils.CityNameGenerator;
import common.utils.CommonCalculations;
import common.utils.EnterAllData;

public class WeatherPageTest extends BaseNDTV {

	HomePageNDTV homepageNdtv;
	WeatherPageNDTV weatherPageNdtv;
	String tempdatacollector;
	int tempInDegreeC;
	int humidity;
	String windSpeed;
	String weatherCondition;

	@BeforeMethod
	public void SetupPage() {
		Setup();
		homepageNdtv = new HomePageNDTV();
		homepageNdtv.OpenExtendedMenu();
		weatherPageNdtv = homepageNdtv.ClickWeatherLink();
	}

	// @Test
	public void presenceOfCityInput() { // to test if "Pin your city" present on page
		Assert.assertEquals(weatherPageNdtv.cityInputFieldEnabled(), true);
	}

	// @Test(dataProvider = "getCities")
	public void enterCityNameTest(String City) { // This will enter city name in text-field and verify on the map
		homepageNdtv.ClickWeatherLink();

		if (weatherPageNdtv.presenceOfCityonList(City)) {
			try {
				weatherPageNdtv.clickCityOnMap(City);
			} catch (Exception e) {
				e.printStackTrace();
				clickOn(driver, driver.findElement(By.xpath("//span[@id='icon_holder']")), 15);
				weatherPageNdtv.enterCityName(City);
				weatherPageNdtv.clickCityOnMap(City);
			}
		}

		homepageNdtv.ClickWeatherLink();
		weatherPageNdtv.enterCityName(City);
		Assert.assertEquals(weatherPageNdtv.presenceOfCityonList("Lucknow"), true);
	}

	@Test(dataProvider = "getCities") // (dependsOnMethods = { "enterCityNameTest" })
	public void collectData(String City) {
		//This Arraylist will contain 4 informations - LiveTemp, Humidity, WindSpeed & Weather Conditions
		ArrayList<Object> cityWeatherData = new ArrayList<Object>();
		try {
			weatherPageNdtv.clickCityOnMap(City); // first it will try finding City on Map directly

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clickOn(driver, driver.findElement(By.xpath("//span[@id='icon_holder']")), 15);
			weatherPageNdtv.enterCityName(City); // By now framework knows on NDTV map test City is not displayed by
													// default, so it will enter City name in lookup
			weatherPageNdtv.clickCityOnMap(City); // this should bring up the weather pop-up with weather informations
													// in detail

		}

		// Now the Weather information of City has opened so lets get the data for further String manipulation for 
		//test data extraction
		tempdatacollector = driver.findElement(By.xpath("//*[@id='map_canvas' and contains(., '" + City + "')]"))
				.getText(); //Stores all weather data as one string
		tempInDegreeC = CommonCalculations.returnTemperatureInDegreeC(tempdatacollector); //Method returns Temp in degree Centigrade
		cityWeatherData.add(tempInDegreeC); 
		
		//Returns Humidity percent after String manipulations
		humidity = CommonCalculations.returnHumidity(tempdatacollector);
		cityWeatherData.add(humidity); 
		//Return Humidity windspeed in m/s after String manipulations
		windSpeed = CommonCalculations.returnWindSpeed(tempdatacollector);
		cityWeatherData.add(windSpeed);
		//Returns Weather conditions like "Overcast" or "Rain" after string manipulations
		weatherCondition = CommonCalculations.returnWeatherCondition(tempdatacollector);
		cityWeatherData.add(weatherCondition);

		
		  for (Object object : cityWeatherData) {
		  System.out.println(String.valueOf(object)); }
		 
		 //Enters 4 weather data points in the Excel sheet
		EnterAllData.enterNDTVData(cityWeatherData, listOfCities, City);
		Assert.assertEquals(
				(tempdatacollector.contains("Temp in Degrees: "+String.valueOf(tempInDegreeC))),
				true, "Temperature data MISSING on weather pop up panel");

	}

	@DataProvider // will return cities names as String
	public Iterator<String> getCities() {
		ArrayList<String> it = CityNameGenerator.storeData();
		return it.iterator();
	}

}
