package com.lovesher.excel;

import java.util.List;

public class ReadTest {

	public static void main(String[] args) throws Exception {

		String file = "D://yx-advanced.xlsx";
		ExcelReader reader = new ExcelReader() {
			public void getRows(int sheetIndex, int curRow, List<String> rowList) {
				System.out.println("Sheet:" + sheetIndex + ", Row:" + curRow + ", Data:" + rowList);
			}
		};
		reader.process(file, 1);
		reader.process(file, 2);
	}
}
