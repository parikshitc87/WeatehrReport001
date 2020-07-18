package com.ndtv.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ndtv.base.BaseNDTV;

public class HomePageNDTV extends BaseNDTV {
	
	@FindBy(xpath = "//a[@class='topnavmore']")
	WebElement threeDotMenu;
	
	@FindBy(xpath = "//a[text()='WEATHER']")
	WebElement weatherLink; 
	
	//Initializing Page objects (only relevant ones)

	public HomePageNDTV() {
		PageFactory.initElements(driver, this);
	}
	
	
	
	public void OpenExtendedMenu() {
		threeDotMenu.click(); // this action will open the extended menu which contains Weather link
	}
	
	public boolean WeatherLinkPresent() {
		return weatherLink.isDisplayed(); //will return true if Weather link is available after user clicks open 3 dot menu
	}
	
	public WeatherPageNDTV ClickWeatherLink() {
		weatherLink.click(); //Will click on the link to reach Weather page
		return new WeatherPageNDTV(); //for page chaining 
	}
	
	
	
	
}
