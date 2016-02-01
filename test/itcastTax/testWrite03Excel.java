package itcastTax;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class testWrite03Excel {

	@Test
	public void testWrite03Excel1() throws Exception {
		//创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("helloworld");
		//创建行
		HSSFRow row = sheet.createRow(2);
		//创建单元格
		HSSFCell cell = row.createCell(2);
		cell.setCellValue("HelloWorld");
		
		//输出到硬盘
		FileOutputStream outputStream = new FileOutputStream("D:\\itcast\\测试.xls");
		
		workbook.write(outputStream);
		
		workbook.close();
		outputStream.close();
		
	}
	@Test
	public void testWrite07Excel1() throws Exception {
		//创建工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		//创建工作表
		XSSFSheet sheet = workbook.createSheet("helloworld");
		//创建行
		XSSFRow row = sheet.createRow(2);
		//创建单元格
		XSSFCell cell = row.createCell(2);
		cell.setCellValue("HelloWorld");
		
		//输出到硬盘
		FileOutputStream outputStream = new FileOutputStream("D:\\itcast\\测试07.xlsx");
		
		workbook.write(outputStream);
		
		workbook.close();
		outputStream.close();
		
	}
	@Test
	public void testRead03Excel2() throws Exception {
		
		FileInputStream inputStream = new FileInputStream("D:\\itcast\\测试.xls");
		
		//读取工作薄
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		//读取第一个工作表
		HSSFSheet sheet = workbook.getSheetAt(0);
		//创建行
		HSSFRow row = sheet.getRow(2);
		//创建单元格
		HSSFCell cell = row.getCell(2);
		System.out.println(cell.getStringCellValue());
		
		workbook.close();
		
	}

}
