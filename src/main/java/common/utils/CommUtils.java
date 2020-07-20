//  Author - Parikshit
package common.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.ndtv.base.BaseNDTV;

public class CommUtils extends BaseNDTV {
	
	//Defining commonly usable objects here
	
	public static int Page_LoadOut_Time = 15;
	public static int Implicitely_Wait = 10;
	
	public static double Variance_difference = 3; //for Temp calculation 
	public static int Humidity_Variance_Percent = 10; //for humidity calculation (in Percent)
	
	

}
