package com.weatherData.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ndtv.BaseNDTV;

import ndtv.pages.HomePageNDTV;
import ndtv.pages.WeatherPageNDTV;

public class WeatherPageTest extends BaseNDTV {

	HomePageNDTV homepageNdtv;
	WeatherPageNDTV weatherPageNdtv;
	
	
	@BeforeMethod
	public void SetupPage() {
		Setup();
		homepageNdtv = new HomePageNDTV();
		weatherPageNdtv = homepageNdtv.ClickWeatherLink();
	}
	
	@Test
	public void presenceOfCityInput() {
		
	}


	
}
