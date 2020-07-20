//  Author - Parikshit

package common.utils;


import java.util.ArrayList;

public class CityNameGenerator {
	public static Xls_Reader reader;
	static String xlsLocation = 
			System.getProperty("user.dir")+"\\src\\main\\resources\\test\\data\\dataExcel.xlsx";

	public static ArrayList<String> storeData() {

		ArrayList<String> excelAllCities = new ArrayList<String>();
		try {
			reader = new Xls_Reader(xlsLocation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this will read sheet's first column from second row to get all the cities
		for (int rowNum = 2; rowNum <= reader.getRowCount("listOfCities"); rowNum++) {

			String City = reader.getCellData("listOfCities", "City", rowNum);
			
			excelAllCities.add(City);

		}
		return excelAllCities; //This will return the Arraylist with all the cities in the excel sheet

	}
}
