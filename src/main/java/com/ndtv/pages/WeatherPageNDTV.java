//  Author - Parikshit

package com.ndtv.pages;

import java.util.concurrent.TimeUnit;

import javax.crypto.KeyAgreementSpi;
import javax.xml.xpath.XPath;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ndtv.base.BaseNDTV;

import common.utils.CommUtils;

public class WeatherPageNDTV extends BaseNDTV {

	// defining WebElements
	@FindBy(xpath = "//input[@type='text' and @class  = 'searchBox']")
	public WebElement CityInputField;

	public WeatherPageNDTV() {
		PageFactory.initElements(driver, this);
	}

	public boolean cityInputFieldEnabled() {
		return CityInputField.isEnabled();
	}

	public void enterCityName(String City) {

		for (char c : City.toCharArray()) {
			CityInputField.click();
			CityInputField.sendKeys(String.valueOf(c), Keys.ENTER); // , Keys.ENTER
			// driver.manage().timeouts().implicitlyWait(CommUtils.Implicitely_Wait,
			// TimeUnit.SECONDS);
			CityInputField.sendKeys(Keys.SPACE, Keys.BACK_SPACE);
			// CityInputField.sendKeys(Keys.RETURN);
		}
		CityInputField.sendKeys(Keys.TAB, Keys.SPACE);

	}

	public void sendTabSpace() {
		CityInputField.click();
		CityInputField.sendKeys(Keys.TAB, Keys.SPACE);
	}

	public boolean presenceOfCityonList(String City) {// This will enter city name in text-field and verify on the map
		return driver.findElement(By.xpath("//input[@id='" + City + "']")).isEnabled();
	}

	public void clickCityOnMap(String City) { // Will click on City displayed on map to bring open panel with weather
												// data
		clickOn(driver, driver.findElement(By.xpath("//div[@class='cityText' and text()='" + City + "']")), 5);
	}

	public boolean isCityOnMap(String City) {
		return driver.findElement(By.xpath("//*[@class = 'outerContainer' and contains(.,'" + City
				+ "')]//div[@class='temperatureContainer']//span[@class='tempRedText']")).isEnabled();
	}
	

}
