package com.ndtv.testcases;

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

//import bsh.This;
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

	@Test(priority = 1)
	public void presenceOfCityInput() { // to test if "Pin your city" present on page
		Assert.assertEquals(weatherPageNdtv.cityInputFieldEnabled(), true);
	}

	
	// This will test Presence Of city on map and panel after clicking on city
	@Test(dataProvider = "getCities", priority = 2)
	public void cityOnMapTest(String City) throws Exception{ 

		if (weatherPageNdtv.presenceOfCityonList(City)) {
			try {
				weatherPageNdtv.clickCityOnMap(City);// first it will try finding and clicking on City on Map directly
			} catch (Exception e) {
				e.printStackTrace();
				clickOn(driver, driver.findElement(By.xpath("//span[@id='icon_holder']")), 15);
				weatherPageNdtv.enterCityName(City);// By now framework knows on NDTV map test City is not displayed by
													//default. So it enters City in lookup and selects it.
				weatherPageNdtv.clickCityOnMap(City);// And this should bring up the weather pop-up 
													// with weather informations in detail. Asserted right ahead.
			}
		}

        Assert.assertTrue((weatherPageNdtv.isCityOnMap(City) || driver
                .findElement(By.xpath("*[@id='map_canvas' and contains(., '" + City + "')]")).isDisplayed())); // verifies City's Presence on Map and if Weather panel opened
	}

	
	//Asserts if the panel contains the temperature details + captures all weather related data and stores in excel sheet
	@Test(dataProvider = "getCities", priority = 3) // (dependsOnMethods = { "enterCityNameTest" })
	public void collectDataTest(String City) {
		// This Arraylist will contain 3 informations - 
		// LiveTemp, Humidity & Weather Conditions
		ArrayList<Object> cityWeatherData = new ArrayList<Object>();
		try {
			weatherPageNdtv.clickCityOnMap(City); 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clickOn(driver, driver.findElement(By.xpath("//span[@id='icon_holder']")), 15);
			weatherPageNdtv.enterCityName(City); 
			weatherPageNdtv.clickCityOnMap(City); 

		}

		// Now the Weather information of City has opened so lets get the data for
		// further String manipulation to extract test data 
		
		tempdatacollector = driver.findElement(By.xpath("//*[@id='map_canvas' and contains(., '" + City + "')]"))
				.getText(); // Stores complete weather data on panel as one string
		//System.out.println(tempdatacollector); //use it to debug
		tempInDegreeC = CommonCalculations.returnTemperatureInDegreeC(tempdatacollector); // Method returns Temp in
																							// degree Centigrade
		cityWeatherData.add(tempInDegreeC);

		// Returns Humidity percent after String manipulations
		humidity = CommonCalculations.returnHumidity(tempdatacollector);
		cityWeatherData.add(humidity);
		/*
		 * // Return Humidity wind speed in m/s after String manipulations windSpeed =
		 * CommonCalculations.returnWindSpeed(tempdatacollector);
		 * cityWeatherData.add(windSpeed);
		 */
		// Returns Weather conditions like "Overcast" or "Rain" after string
		// manipulations
		weatherCondition = CommonCalculations.returnWeatherCondition(tempdatacollector);
		cityWeatherData.add(weatherCondition);

		for (Object object : cityWeatherData) {
			System.out.println(String.valueOf(object));
		}

		// Enters 4 weather data points in the Excel sheet
		EnterAllData.enterNDTVData(cityWeatherData, listOfCities, City);
		//Asserts if the panel contains the temperature details
        Assert.assertTrue((tempdatacollector.contains("Temp in Degrees: " + String.valueOf(tempInDegreeC))), "Temperature data MISSING on weather pop up panel");

	}

}
