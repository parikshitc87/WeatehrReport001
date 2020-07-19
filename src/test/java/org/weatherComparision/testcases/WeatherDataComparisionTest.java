package org.weatherComparision.testcases;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.math3.analysis.interpolation.DividedDifferenceInterpolator;
import org.openweathermap.BaseOpenWeatherMap;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.utils.CityNameGenerator;
import common.utils.CommUtils;
import common.utils.EnterAllData;
import common.utils.Xls_Reader;

public class WeatherDataComparisionTest extends BaseOpenWeatherMap {
	public static Xls_Reader reader;
	static String xlsLocation = System.getProperty("user.dir") + "\\src\\main\\resources\\test\\data\\dataExcel.xlsx";
	String listOfCities = "listOfCities";

	@BeforeMethod
	public void setup() {
		try {
			reader = new Xls_Reader(xlsLocation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(dataProvider = "getCities")
	public void compareLiveTemps(String City) { // here we will test if NDTV data is within 10% of data from
												// OpenWeatherMap or
		// not

		System.out.println(reader.getRowCount("listOfCities"));

		System.out.println(reader.getCellData(listOfCities, "LiveTemp (OpenWeatherMap)",
				reader.getCellRowNum(listOfCities, "City", City)));
		System.out.println(
				reader.getCellData(listOfCities, "LiveTemp (NDTV)", reader.getCellRowNum(listOfCities, "City", City)));

		double liveTempOWM = Double.parseDouble(reader.getCellData(listOfCities, "LiveTemp (OpenWeatherMap)",
				reader.getCellRowNum(listOfCities, "City", City)));
		double liveTempNDTV = Double.parseDouble(
				reader.getCellData(listOfCities, "LiveTemp (NDTV)", reader.getCellRowNum(listOfCities, "City", City)));

		double differenceInTemp = Math.abs(liveTempOWM - liveTempNDTV);
		
		Double variance = CommUtils.Variance_difference;

		// System.out.println("Variance Percentage is : " + variancePercent);

		if (variance >= differenceInTemp) {
			boolean flag = true;
			reader.setCellData(listOfCities, "LiveTemp Compare Result",
					reader.getCellRowNum(listOfCities, "City", City), "Temperatues are within Variance Range");
			Assert.assertEquals(flag, true);
		} else {
			boolean flag = false;
			reader.setCellData(listOfCities, "LiveTemp Compare Result",
					reader.getCellRowNum(listOfCities, "City", City), "Temperatues are NOT within Variance Range");
			Assert.assertEquals(flag, true);
		}

	}

	@Test(dataProvider = "getCities")
	public void compareWeatherConditions(String City) {
		int weatherSimilarityMeter = 0;
		boolean cloudy1 = false;
		boolean cloudy2 = false;

		String weatherConditionOWM = reader.getCellData(listOfCities, "Weather Condition (OpenWeatherMap)",
				reader.getCellRowNum(listOfCities, "City", City)).toLowerCase();
		String weatherConditionNDTV = reader
				.getCellData(listOfCities, "Weather Condition (NDTV)", reader.getCellRowNum(listOfCities, "City", City))
				.toLowerCase();

		if (weatherConditionOWM.contains("mist") && weatherConditionNDTV.contains("humid"))
			weatherSimilarityMeter++;
		

		String array1[] = new String[] { "haze", "clouds" };
		String array2[] = new String[] { "overcast", "cloudy" };

		loopOne: for (String str1 : array1) {
			for (String str2 : weatherConditionOWM.split("\\s")) {
				if (str1.equals(str2)) {
					cloudy1 = true;
					break loopOne;
				}
			}
		}

		loopTwo: for (String str1 : array2) {
			for (String str2 : weatherConditionNDTV.split("\\s")) {
				if (str1.equals(str2)) {
					cloudy2 = true;
					break loopTwo;
				}
			}
		}

		if (cloudy1 && cloudy2) 
			weatherSimilarityMeter++;
		

		if (weatherConditionOWM.contains("rain") && weatherConditionNDTV.contains("rain")) 
			weatherSimilarityMeter++;
		

		System.out.println(weatherConditionOWM);
		System.out.print(weatherConditionNDTV);
		System.out.println("Flag count -- " + weatherSimilarityMeter);

		if (weatherSimilarityMeter == 0) {
			reader.setCellData(listOfCities, "Weather Condition Compare Result",
					reader.getCellRowNum(listOfCities, "City", City),
					"Both portals show different Weather conditions!");
		} else if (weatherSimilarityMeter == 1) {
			reader.setCellData(listOfCities, "Weather Condition Compare Result",
					reader.getCellRowNum(listOfCities, "City", City),
					"Both portals show slightly similar Weather conditions!");
		}

		else if (weatherSimilarityMeter == 2) {
			reader.setCellData(listOfCities, "Weather Condition Compare Result",
					reader.getCellRowNum(listOfCities, "City", City),
					"Both portals show almost similar Weather conditions!");

		}

	}

	@Test(dataProvider = "getCities")
	public void compareHumidity(String City) {
		double humidityOWM = Integer.parseInt(reader.getCellData(listOfCities, "Humidity (OpenWeatherMap)",
				reader.getCellRowNum(listOfCities, "City", City)));
		double humidityNDTV = Integer.parseInt(
				reader.getCellData(listOfCities, "Humidity (NDTV)", reader.getCellRowNum(listOfCities, "City", City)));

		System.out.println("OpenWeatherMap Humidity:  " + humidityOWM);
		System.out.println("NDTV Humidity:  " + humidityNDTV);

		double differenceInTemp = Math.abs(humidityOWM - humidityNDTV);
		System.out.println(differenceInTemp);
		
		
		double percentOWM = (differenceInTemp / humidityOWM) * 100;
		double percentNDTV = (differenceInTemp / humidityNDTV) * 100;
		
		System.out.println(percentOWM+"***"+percentNDTV);
		
//		/int variancePercent = CommUtils.Humidity_Variance_Percent;
		
		if (percentOWM <= CommUtils.Humidity_Variance_Percent || percentNDTV <= CommUtils.Humidity_Variance_Percent) {
			boolean flag = true;
			reader.setCellData(listOfCities, "Humidity Compare Result",
					reader.getCellRowNum(listOfCities, "City", City), "Humidity readings are within Variance Range");
			Assert.assertEquals(flag, true);
		} else {
			boolean flag = false;
			reader.setCellData(listOfCities, "Humidity Compare Result",
					reader.getCellRowNum(listOfCities, "City", City), "Humidity readings are NOT within Variance Range");
			Assert.assertEquals(flag, true);
		}
	}
	
	@Test(dataProvider = "getCities")
	public void compareWindSpeed(String City) {
		double windSpeedOWM = Double.parseDouble(reader.getCellData(listOfCities, "Wind (OpenWeatherMap)", reader.getCellRowNum(listOfCities, "City", City)));
		double windSpeedNDTV = Double.parseDouble(reader.getCellData(listOfCities, "Wind (NDTV)", reader.getCellRowNum(listOfCities, "City", City)));
		
		if (windSpeedNDTV > windSpeedOWM) {
			reader.setCellData(listOfCities, "Wind Compare Result", reader.getCellRowNum(listOfCities, "City", City), "Wind speed is faster on NDTV.");
		}
		else if (windSpeedNDTV < windSpeedOWM) {
			reader.setCellData(listOfCities, "Wind Compare Result", reader.getCellRowNum(listOfCities, "City", City), "Wind speed is faster on Open Weather Map.");
		}
		else {
			reader.setCellData(listOfCities, "Wind Compare Result", reader.getCellRowNum(listOfCities, "City", City), "Wind speed is same on both portals.");

		}
		
	}

	@DataProvider // will return cities names as String one by one
	public Iterator<String> getCities() {
		ArrayList<String> it = CityNameGenerator.storeData();
		return it.iterator();
	}

}
