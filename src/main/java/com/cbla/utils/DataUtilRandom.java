package com.cbla.utils;

import java.util.Random;

public class DataUtilRandom {
    //TestNG Parameterization
    public static Object[][] getData(String sheetName, ResourceExcelReader excel) {
        // return test data;
        // read test data from xls

        int rows = excel.getRowCount(sheetName);
        if (rows <= 0) {
            Object[][] testData = new Object[1][0];
            return testData;
        }
        rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);
        Random randomizer = new Random();
        int rowNum = randomizer.nextInt(rows - 1) + 2;
        Object data[][] = new Object[1][cols];


        for (int colNum = 0; colNum < cols; colNum++) {
            data[rowNum - rowNum][colNum] = excel.getCellData(sheetName, colNum, rowNum);
        }

        return data;
    }

}
