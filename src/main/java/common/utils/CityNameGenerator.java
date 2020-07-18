package common.utils;

import java.util.ArrayList;

public class CityNameGenerator {
	static Xls_Reader reader;
	static String xlsLocation = 
			System.getProperty("user.dir")+"\\src\\main\\resources\\com\\test\\data\\dataExcel.xlsx";

	public static ArrayList<String[]> storeData() {

		ArrayList<String[]> excelAllData = new ArrayList<String[]>();
		try {
			reader = new Xls_Reader(xlsLocation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int rowNum = 2; rowNum <= reader.getRowCount("Sheet1"); rowNum++) {

			String firstName = reader.getCellData("Sheet1", "First Name", rowNum);
			

			Object ob[] = { firstName, lastName, e_Mail, phoneNum, address, city, state, zipCode, website,
					doYouHaveHosting, projectDesc };
			excelAllData.add(ob);

		}
		return excelAllData;

	}
}
