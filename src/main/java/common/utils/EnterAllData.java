//  Author - Parikshit

package common.utils;

import java.util.ArrayList;

import org.openweathermap.BaseOpenWeatherMap;

public class EnterAllData extends BaseOpenWeatherMap {

	// To fill up data captured from OpenWeatherMap.org into excel sheet
	public static void enterOpenWeatherMapData(ArrayList<Object> arr, String listOfCities, int dataEntryFlag,
			String City) {

		int i = 0;
		CityNameGenerator.reader.setCellData(listOfCities, "LiveTemp (OpenWeatherMap)",
				CityNameGenerator.reader.getCellRowNum(listOfCities, "City", City), String.valueOf(arr.get(i)));
		CityNameGenerator.reader.setCellData(listOfCities, "Humidity (OpenWeatherMap)",
				CityNameGenerator.reader.getCellRowNum(listOfCities, "City", City), String.valueOf(arr.get(i + 1)));
		CityNameGenerator.reader.setCellData(listOfCities, "Wind (OpenWeatherMap)",
				CityNameGenerator.reader.getCellRowNum(listOfCities, "City", City), String.valueOf(arr.get(i + 2)));
		CityNameGenerator.reader.setCellData(listOfCities, "Weather Condition (OpenWeatherMap)",
				CityNameGenerator.reader.getCellRowNum(listOfCities, "City", City), String.valueOf(arr.get(i + 3)));
		//dataEntryFlag++;

	}

	// To fill up data captured from NDTV.com into excel sheet
	public static void enterNDTVData(ArrayList<Object> arr, String listOfCities, String City) {

		int i = 0;
		CityNameGenerator.reader.setCellData(listOfCities, "LiveTemp (NDTV)",
				CityNameGenerator.reader.getCellRowNum(listOfCities, "City", City), String.valueOf(arr.get(i)));
		CityNameGenerator.reader.setCellData(listOfCities, "Humidity (NDTV)",
				CityNameGenerator.reader.getCellRowNum(listOfCities, "City", City), String.valueOf(arr.get(i + 1)));
		CityNameGenerator.reader.setCellData(listOfCities, "Wind (NDTV)",
				CityNameGenerator.reader.getCellRowNum(listOfCities, "City", City), String.valueOf(arr.get(i + 2)));
		CityNameGenerator.reader.setCellData(listOfCities, "Weather Condition (NDTV)",
				CityNameGenerator.reader.getCellRowNum(listOfCities, "City", City), String.valueOf(arr.get(i + 3)));

	}

}
