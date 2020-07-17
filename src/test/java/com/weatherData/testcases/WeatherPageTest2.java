package com.weatherData.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ndtv.BaseNDTV;

import common.utils.CommonCalculations;
import ndtv.pages.HomePageNDTV;
import ndtv.pages.WeatherPageNDTV;

public class WeatherPageTest2 extends BaseNDTV {

	HomePageNDTV homepageNdtv;
	WeatherPageNDTV weatherPageNdtv;
	String tempdatacollector;
	int tempInDegreeC;
	
	

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
	public void enterCityNameTest() { //This will enter city name in text-field and verify on the map
		weatherPageNdtv.enterCityName("Lucknow");
		//System.out.println();
		Assert.assertEquals(weatherPageNdtv.presenceOfCityonList("Lucknow"), true);
		
	}
	
	@Test//(dependsOnMethods = { "enterCityNameTest" })
	public void selectCityCheckbox(){
		
		
		/*
		 * if(!driver.findElement(By.
		 * xpath("//div[@class='cityText' and contains(. ,'Ajmer')]")).isDisplayed()) {
		 * System.out.println("Ajmer Displayed");
		 * weatherPageNdtv.clickCityOnMap("Ajmer"); }
		 */
		
		driver.findElement(By.xpath("//span[@id='icon_holder']")).click();
		
		weatherPageNdtv.enterCityName("Ajmer");
		
		
		/*
		 * if (!driver.findElement(By.
		 * xpath("//div[@class='cityText' and contains(. ,'Ajmer')]")).isDisplayed()) {
		 * weatherPageNdtv.clickToSelectCity("Ajmer"); }
		 */
		
		if (!driver.findElement(By.xpath("//div[@class='cityText' and contains(. ,'Ajmer')]")).isDisplayed()) {
			System.out.println("Ajmer");
		}
		
	
		weatherPageNdtv.clickCityOnMap("Ajmer");		
		System.out.println(driver.findElement(By.xpath("//*[@id='map_canvas' and contains(., 'Temp in Degrees')]")).getText());
		tempdatacollector = driver.findElement(By.xpath("//*[@id='map_canvas' and contains(., 'Temp in Degrees')]")).getText();
		tempInDegreeC = CommonCalculations.returnTemperatureInDegreeC(tempdatacollector);
		
	}
	
	
	@Test
	public void makesureCityDisplayedonMap() {
		
	}
	
	

}
