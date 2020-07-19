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
		// boolean flag = false;

		// calculating if NDTV data is within 10% of openWeatherMap data
		// and Vice Versa, because opposite might not hold true

		double percentOWM = (differenceInTemp / liveTempOWM) * 100;
		double percentNDTV = (differenceInTemp / liveTempNDTV) * 100;

		Double variancePercent = CommUtils.Variance_Percent;

		// System.out.println("Variance Percentage is : " + variancePercent);

		if (percentOWM <= variancePercent || percentNDTV <= variancePercent) {
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
		int weatherConditionMeter = 0;
		boolean cloudy1 = false;
		boolean cloudy2 = false;

		String weatherConditionOWM = reader.getCellData(listOfCities, "Weather Condition (OpenWeatherMap)",
				reader.getCellRowNum(listOfCities, "City", City)).toLowerCase();
		String weatherConditionNDTV = reader
				.getCellData(listOfCities, "Weather Condition (NDTV)", reader.getCellRowNum(listOfCities, "City", City))
				.toLowerCase();

		System.out.println(weatherConditionOWM);
		System.out.println(weatherConditionNDTV);

		if (weatherConditionOWM.contains("mist") && weatherConditionNDTV.contains("humid")) {
			weatherConditionMeter++;
		}
		String array1[] = new String[] { "haze", "clouds"};
		String array2[] = new String[] { "overcast", "cloudy" };
		
		loopOne:
		for( String str1 : array1 ) {
			for(String str2 : weatherConditionOWM.split("\\s")) {
				if (str1.equals(str2)) {
					cloudy1 = true;
					break loopOne;
				}
			}
		}
		
		loopTwo:
			for( String str1 : array2 ) {
				for(String str2 : weatherConditionNDTV.split("\\s")) {
					if (str1.equals(str2)) {
						cloudy2 = true;
						break loopTwo;
					}
				}
			}
		
		if(cloudy1 && cloudy2) {
			weatherConditionMeter++;
		}
		
		

		if (weatherConditionOWM.contains("rain") && weatherConditionNDTV.contains("rain")) {
			weatherConditionMeter++;
		}

		System.out.println("Flag count -- " + weatherConditionMeter);

		if (weatherConditionMeter == 0) {
			reader.setCellData(listOfCities, "Weather Condition Compare Result",
					reader.getCellRowNum(listOfCities, "City", City),
					"Both portals show different Weather conditions!");
		} else if (weatherConditionMeter == 1) {
			reader.setCellData(listOfCities, "Weather Condition Compare Result",
					reader.getCellRowNum(listOfCities, "City", City),
					"Both portals show slightly similar Weather conditions!");
		}

		else if (weatherConditionMeter == 2) {
			reader.setCellData(listOfCities, "Weather Condition Compare Result",
					reader.getCellRowNum(listOfCities, "City", City),
					"Both portals show almost similar Weather conditions!");

		}

	}
	
	
	@Test(dataProvider = "getCities")
	public void compareHumidity() {
		
	}

	@DataProvider // will return cities names as String one by one
	public Iterator<String> getCities() {
		ArrayList<String> it = CityNameGenerator.storeData();
		return it.iterator();
	}

}
