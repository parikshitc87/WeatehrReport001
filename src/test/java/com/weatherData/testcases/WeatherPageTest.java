package com.weatherData.testcases;

import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ndtv.base.BaseNDTV;
import com.ndtv.pages.HomePageNDTV;
import com.ndtv.pages.WeatherPageNDTV;

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

	@Test
	public void presenceOfCityInput() {
		Assert.assertEquals(weatherPageNdtv.cityInputFieldEnabled(), true);
	}

	@Test(dependsOnMethods = { "presenceOfCityInput" })
	public void enterCityNameTest() { // This will enter city name in text-field and verify on the map
		weatherPageNdtv.enterCityName("Lucknow");
		// System.out.println();
		Assert.assertEquals(weatherPageNdtv.presenceOfCityonList("Lucknow"), true);

	}

	@Test (dataProvider = "getCities")// (dependsOnMethods = { "enterCityNameTest" })
	public void collectData(String City) {
		ArrayList<Object> cityWeatherData = new ArrayList<Object>();
		try {
			weatherPageNdtv.clickCityOnMap(City);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clickOn(driver, driver.findElement(By.xpath("//span[@id='icon_holder']")), 15);
			weatherPageNdtv.enterCityName(City);
			
			//weatherPageNdtv.sendTabSpace(); 
			 
			weatherPageNdtv.clickCityOnMap(City);
		}

		
		//now the Weather information of City has opened so lets get the data
		System.out.println(
				driver.findElement(By.xpath("//*[@id='map_canvas' and contains(., '"+City+"')]")).getText());
		tempdatacollector = driver.findElement(By.xpath("//*[@id='map_canvas' and contains(., '"+City+"')]"))
				.getText();
		tempInDegreeC = CommonCalculations.returnTemperatureInDegreeC(tempdatacollector);
		cityWeatherData.add(tempInDegreeC);
		
		humidity = CommonCalculations.returnHumidity(tempdatacollector);
		cityWeatherData.add(humidity);
		
		windSpeed = CommonCalculations.returnWindSpeed(tempdatacollector);
		cityWeatherData.add(windSpeed);
		
		weatherCondition = CommonCalculations.returnWeatherCondition(tempdatacollector);
		cityWeatherData.add(weatherCondition);
		
		for(Object object : cityWeatherData ) {
			System.out.println(String.valueOf(object));
		}
		
		EnterAllData.enterNDTVData(cityWeatherData, listOfCities,  City);
		
		
	}
	
	
	
	@DataProvider //will return cities names as String one by one
	public Iterator<String> getCities() {
		ArrayList<String> it = CityNameGenerator.storeData();
		return it.iterator();
	}
	

}
