package common.utils;

public class CommonCalculations {

	public static int returnTemperatureInDegreeC(String str) {
		str = str.substring(str.indexOf("Temp in Degrees") + 17, str.indexOf("Temp in Degrees") + 19);
		System.out.println("Integer is " + Integer.parseInt(str));
		return Integer.parseInt(str);
	}

}
