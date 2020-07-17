package com.weatherData.testcases;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ndtv.BaseNDTV;

import ndtv.pages.HomePageNDTV;
//import pages.SeleniumTestPage;



public class HomePageTest extends BaseNDTV{
	HomePageNDTV homepageNdtv;
	
	
	@BeforeMethod
	public void SetupPage() {
		homepageNdtv = new HomePageNDTV();
	}
	
	
	
	
	@Test
	public void  verifyHomePageTitleTest() {  // to check if we have landed at the desired page
		System.out.println(driver.getTitle());
		homepageNdtv.OpenExtendedMenu();
		Assert.assertEquals(driver.getTitle(), "NDTV: Latest News, India News, Breaking News, Business, Bollywood, Cricket, Videos & Photos");
	}
	
	@Test
	public void presenceOfWeatherLinkTest() {
		assertEquals(homepageNdtv.WeatherLinkPresent(), true);
		homepageNdtv.ClickWeatherLink();
	}

	
	
}
