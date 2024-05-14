package excelReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	XSSFWorkbook workbook;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	String excelPath;

	XSSFSheet sheet;

	public ExcelReader(String excelPath)
	{
		this.excelPath = excelPath;
		try
		{
			fis = new FileInputStream(excelPath);
			workbook = new XSSFWorkbook(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// find whether sheets exists
		public boolean isSheetExist(String sheetName) {
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1) {
				index = workbook.getSheetIndex(sheetName.toUpperCase());
				if (index == -1)
					return false;
				else
					return true;
			} else
				return true;
		}

	// returns the row count in a sheet
	public int getRowCount(String sheetName)
	{
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
		{
			return 0;
		} else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}

	}

	// returns number of columns in a sheet
		public int getColumnCount(String sheetName) {
			// check if sheet exists
			if (!isSheetExist(sheetName))
				return -1;

			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);

			if (row == null)
				return -1;

			return row.getLastCellNum();

		}

	// returns the data from a cell
	public String getCellData(String sheetName, String colName, int rowNum)
	{
		int index = workbook.getSheetIndex(sheetName);
		sheet = workbook.getSheetAt(index);
		XSSFRow row = sheet.getRow(0);
		int col_Num = 0;
		for (int i = 0; i < row.getLastCellNum(); i++)
		{
			if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
			{
				col_Num = i;
				break;
			}
		}

		row = sheet.getRow(rowNum - 1);
		XSSFCell cell = row.getCell(col_Num);
		if (cell == null)
		{
			// System.out.println("cell is not present");
			return "";
		}

		switch (cell.getCellType())
		{
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			System.out.println("no matching enum date type found");
			break;
		}

		return colName;

	}


	// returns the data from a cell
			public String getCellData(String sheetName, int colNum, int rowNum) {
				try {
					if (rowNum <= 0)
						return "";

					int index = workbook.getSheetIndex(sheetName);

					if (index == -1)
						return "";

					sheet = workbook.getSheetAt(index);
					row = sheet.getRow(rowNum - 1);
					if (row == null)
						return "";
					cell = row.getCell(colNum);
					if (cell == null)
						return "";

					if (cell.getCellType().name().equals("STRING"))
						return cell.getStringCellValue();


					// if (cell.getCellType().STRING != null)
					// return cell.getStringCellValue();
					else if ((cell.getCellType().name().equals("NUMERIC")) || (cell.getCellType().name().equals("FORMULA"))) {

						String cellText = String.valueOf(cell.getNumericCellValue());
						if (DateUtil.isCellDateFormatted(cell)) {
							// format in form of M/D/YY
							double d = cell.getNumericCellValue();

							Calendar cal = Calendar.getInstance();
							cal.setTime(DateUtil.getJavaDate(d));
							cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
							cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

							// System.out.println(cellText);

						}

						return cellText;
					} else {
						cell.getCellType();
						if (CellType.BLANK != null)
							return "";
						else
							return String.valueOf(cell.getBooleanCellValue());
					}
				} catch (Exception e) {

					e.printStackTrace();
					return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
				}
			}


	public boolean setCellData(String sheetName, String colName, int rowNum, String data)
	{
		try {
			fis = new FileInputStream(excelPath);
			workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++)
			{
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			// CellStyle cs = workbook.createCellStyle();
			// cs.setWrapText(true);
			// cell.setCellStyle(cs);
			cell.setCellValue(data);
			fileOut = new FileOutputStream(excelPath);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public boolean setCellData(String sheetName, int colNumber, int rowNum, String data)
    {
        try
        {
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowNum);
            if(row==null)
                row = sheet.createRow(rowNum);

            cell = row.getCell(colNumber);
            if(cell == null)
                cell = row.createCell(colNumber);

            cell.setCellValue(data);

            fileOut = new FileOutputStream(excelPath);
            workbook.write(fileOut);
            fileOut.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return  false;
        }
        return true;
    }




	public static void main(String[] args)
	{
		ExcelReader excelReader = new ExcelReader(
		System.getProperty("user.dir") + "/src/test/java/testData/TestSuite1.xlsx");
		System.out.println(excelReader.getRowCount("TC01"));
		System.out.println(excelReader.getCellData("TC01", "Keyword", 3));
	}

}
