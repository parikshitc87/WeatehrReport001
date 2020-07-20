//  Author - Parikshit

package common.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import freemarker.core.ReturnInstruction.Return;

public class CommonCalculations {
	static DecimalFormat df = new DecimalFormat("0.00"); // to round off to 2 decimal places

	public static int returnTemperatureInDegreeC(String str) {
		String str1 = str.substring(str.indexOf("Temp in Degrees") + 17, str.indexOf("Temp in Degrees") + 19);
		System.out.println("Integer is " + Integer.parseInt(str1));
		return Integer.parseInt(str1);
	}

	public static int returnHumidity(String str) {
		str = str.substring(str.indexOf("Humidity") + 10, str.indexOf("Humidity") + 12);
		System.out.println("Humidity = " + str);
		return Integer.parseInt(str);
	}

	public static String returnWindSpeed(String str) {
		// calculating wind speed 
		df.setRoundingMode(RoundingMode.UP);
		String str1 = str.substring(str.indexOf("Wind") + 6, str.indexOf("Wind") + 10);
		
		System.out.println("*******************");
		System.out.println(str1);
		
	
		// also divided  by 3.6 to get speed in m/s to match OpenWeatherMap wind speed unit (m/s)
		String avgWindSpeedString = df.format(Double.parseDouble(str1)/3.6);
		return avgWindSpeedString;
	}

	public static String returnWeatherCondition(String str) {
		str = str.substring(str.indexOf("Condition :") + 12, str.indexOf("Wind:"));
		System.out.println("Weather Condition : " + str);
		return str; // returns weather condition something like - "Humid and Overcast"
	}

}
