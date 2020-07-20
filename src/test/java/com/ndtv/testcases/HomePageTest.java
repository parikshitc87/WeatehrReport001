package com.ndtv.testcases;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ndtv.base.BaseNDTV;
import com.ndtv.pages.HomePageNDTV;
import com.ndtv.pages.WeatherPageNDTV;




public class HomePageTest extends BaseNDTV{
	HomePageNDTV homepageNdtv;
	WeatherPageNDTV weatherPageNdtv;
	
	
	@BeforeMethod
	public void SetupPage() {
		Setup();
		homepageNdtv = new HomePageNDTV();
		weatherPageNdtv = new WeatherPageNDTV();
	}
	
	
	@Test (priority = 1)
	public void  verifyHomePageTitleTest() {  // Verifies NDTV homepage title 
		Assert.assertEquals(driver.getTitle(), "NDTV: Latest News, India News, Breaking News, Business, Bollywood, Cricket, Videos & Photos", "Homepage Title Mismatch");
	}
	
	@Test (priority = 2)
	public void presenceOfWeatherLinkTest() { //Tests presence of weather link for further navigation
		homepageNdtv.OpenExtendedMenu();
		Assert.assertEquals(homepageNdtv.WeatherLinkPresent(), true); 
	}
	
	@Test (priority = 3)
	public void clickWeatherLinkTest() {	//Clicks on the link
		homepageNdtv.OpenExtendedMenu();
		weatherPageNdtv = homepageNdtv.ClickWeatherLink();
	}
	
	
	
}
