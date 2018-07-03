package com.hellofresh.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelutil {

	public static String getvalue1() throws IOException {

		File src = new File("src/main/java/com/hellofresh/utilities/testdata.xlsx");
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook workbbook=new XSSFWorkbook(fis);
		XSSFSheet sheet1 = workbbook.getSheetAt(0);
		String testdata = sheet1.getRow(0).getCell(0).getStringCellValue();
		return testdata;
	}
	
}