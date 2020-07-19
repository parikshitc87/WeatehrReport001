package common.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import freemarker.core.ReturnInstruction.Return;

public class CommonCalculations {
	static DecimalFormat df = new DecimalFormat("0.00"); //to round off to 2 decimal places


	public static int returnTemperatureInDegreeC(String str) {
		str = str.substring(str.indexOf("Temp in Degrees") + 17, str.indexOf("Temp in Degrees") + 19);
		System.out.println("Integer is " + Integer.parseInt(str));
		return Integer.parseInt(str);
	}
	
	public static int returnHumidity(String str) {
		str = str.substring(str.indexOf("Humidity") + 10, str.indexOf("Humidity") + 12);
		System.out.println("Humidity = "+ str);
		return Integer.parseInt(str);
	}
	
	public static String returnWindSpeed(String str) {
		//calculating average of wind speed and Gusting speed in m/s
		df.setRoundingMode(RoundingMode.UP);
		String str1 = str.substring(str.indexOf("Wind") + 6, str.indexOf("Wind") + 10);
		String str2 = str.substring(str.indexOf("Gust") + 11, str.indexOf("Gust") + 15);
		System.out.println("*******************");
		System.out.println(str1);
		System.out.println(str2);
		double avgWindSpeed = ( Double.parseDouble(str1) + Double.parseDouble(str2) ) / (2 * 3.6 ) ; //dividing the average by 3.6 to get speed in m/s
		String avgWindSpeedString = df.format(avgWindSpeed);
		return avgWindSpeedString;
	}
	
	public static String returnWeatherCondition(String str) {
		str = str.substring(str.indexOf("Condition :") + 12, str.indexOf("Wind"));
		System.out.println("Weather Condition : " + str);
		return str; //returns weather condition something like - "Humid and Overcast"
	}

}
