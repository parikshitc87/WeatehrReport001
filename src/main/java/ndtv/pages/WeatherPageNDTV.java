package ndtv.pages;

import java.util.concurrent.TimeUnit;

import javax.crypto.KeyAgreementSpi;
import javax.xml.xpath.XPath;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.guide.ClickableWindow;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ndtv.BaseNDTV;

import common.utils.CommUtils;

public class WeatherPageNDTV extends BaseNDTV {

	// defining WebElements
	@FindBy(xpath = "//input[@type='text' and @class  = 'searchBox']")
	public WebElement CityInputField;

	// @FindBy(xpath = "//input[@id='temp' and @type = 'checkbox']")

	public WeatherPageNDTV() {
		PageFactory.initElements(driver, this);
	}

	public boolean cityInputFieldEnabled() {
		return CityInputField.isEnabled();
	}

	public void enterCityName(String City) {
		
		for(char c : City.toCharArray()) {
			CityInputField.click();
			CityInputField.sendKeys(String.valueOf(c), Keys.ENTER); // , Keys.ENTER
			//driver.manage().timeouts().implicitlyWait(CommUtils.Implicitely_Wait, TimeUnit.SECONDS);
			CityInputField.sendKeys(Keys.SPACE, Keys.BACK_SPACE);
			
			
			//CityInputField.sendKeys(Keys.RETURN);
		}
		CityInputField.sendKeys(Keys.TAB, Keys.SPACE);
		
		
		
	}

	public boolean presenceOfCityonList(String City) {
		return driver.findElement(By.xpath("//input[@id='"+ City+"']")).isEnabled();
	}
	
	public boolean isCitySelectedInList(String City) {
		return driver.findElement(By.xpath("//input[@id='"+City+"']")).isSelected();
	}
	
	public void clickToSelectCity(String City) {
		clickOn(driver, driver.findElement(By.xpath("//input[@id='"+City+"']")), 15);
	}
	
	public void clickCityCheckBox(String City) {
		clickOn(driver, driver.findElement(By.xpath("//input[@id='"+City+"']")), 15);
	}
	
	public void selectCityCheckbox(String City) {
		Select select = new Select(driver.findElement(By.xpath("//input[@id='"+City+"' and @type  = 'checkbox']")));
		select.selectByVisibleText(City);
		//driver.findElement(By.xpath("//input[@id='"+City+"' and @type  = 'checkbox']")).
	}
	
	public boolean cityDisplayedOnMap(String City) {
		//this will tell if the city is displayed on the map
		return driver.findElement(By.xpath("//div[@class='cityText' and text()='"+City+"']")).isDisplayed();
	}
	
	public void clickCityOnMap(String City) {
		
		clickOn(driver, driver.findElement(By.xpath("//div[@class='cityText' and text()='"+City+"']")) , 15);
		
		//driver.findElement(By.xpath("//div[@class='cityText' and text()='"+City+"']")).click();
	}
	
	
	public boolean weatherPanelDisplay(String City) { //will return true if weather panel is displayed after clicking on city on map
		return driver.findElement(By.xpath("//span[@style='margin-bottom:10px' and contains(.,'"+City+"')]")).isDisplayed();
	}
	
	public boolean isCityDisplayedOnMap(String City) {
		return driver.findElement(By.xpath("//div[@class='cityText' and text()='"+City+"']")).isDisplayed();
	}
	
	/*
	 * public int returnCityTemperature(String City) { // driver.findElement(By.
	 * xpath("//*[@id='map_canvas' and contains(., 'Temp in Degrees')]")).getText();
	 * }
	 */
}
