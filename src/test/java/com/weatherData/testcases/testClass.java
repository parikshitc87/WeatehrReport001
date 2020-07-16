package com.weatherData.testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ndtv.BaseNDTV;

import ndtv.pages.HomePageNDTV;
//import pages.SeleniumTestPage;



public class testClass extends BaseNDTV{
	HomePageNDTV homepageNdtv;
	
	
	
	@BeforeMethod
	public void SetupPage() {

		homepageNdtv = new HomePageNDTV();
	}
	
	
	
	
	@Test
	public void  verifyHomePageTitleTest() {
		homepageNdtv.OpenExtendedMenu();
		
	}
	

}
