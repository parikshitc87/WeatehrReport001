package com.weatherData.testcases;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ndtv.base.BaseNDTV;

import ndtv.pages.HomePageNDTV;
//import pages.SeleniumTestPage;
import ndtv.pages.WeatherPageNDTV;



public class HomePageTest extends BaseNDTV{
	HomePageNDTV homepageNdtv;
	WeatherPageNDTV weatherPageNdtv;
	
	
	@BeforeMethod
	public void SetupPage() {
		Setup();
		homepageNdtv = new HomePageNDTV();
	}
	
	
	
	
	@Test (priority = 1)
	public void  verifyHomePageTitleTest() {  // to check if we have landed at the desired page
		System.out.println(driver.getTitle());
		Assert.assertEquals(driver.getTitle(), "NDTV: Latest News, India News, Breaking News, Business, Bollywood, Cricket, Videos & Photos");
	}
	
	@Test (priority = 2)
	public void presenceOfWeatherLinkTest() {
		homepageNdtv.OpenExtendedMenu();
		Assert.assertEquals(homepageNdtv.WeatherLinkPresent(), true); //test - Is the Weather link present ?
		weatherPageNdtv = homepageNdtv.ClickWeatherLink(); //Navigate to Weather page
	}

	
	
}
