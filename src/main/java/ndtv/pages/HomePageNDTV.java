package ndtv.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ndtv.BaseNDTV;

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
		threeDotMenu.click();

	}
	
	
	
	
	
}
