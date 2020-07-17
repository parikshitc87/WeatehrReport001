package ndtv.pages;

import javax.crypto.KeyAgreementSpi;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ndtv.BaseNDTV;

public class WeatherPageNDTV extends BaseNDTV {

	// defining WebElements
	@FindBy(xpath = "//input[@type='text' and @class  = 'searchBox']")
	WebElement CityInputField;

	public WeatherPageNDTV() {
		PageFactory.initElements(driver, this);
	}

	
	public boolean cityInputFieldEnabled() {
		return CityInputField.isEnabled();
	}
	
	public void enterCityName(String City) {
		CityInputField.sendKeys(City, Keys.ENTER);
		CityInputField.sendKeys(Keys.RETURN);
	}
	
	
}
