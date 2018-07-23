import org.apache.http.impl.io.SocketOutputBuffer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Iterator;

public class FileWork
{

    private Table List;
    private ListArray Array;
    private Info element;
    private String file;
    private int Timer;
    
    FileWork(ListArray Input)
    {
    	file="D://Book.xlsx";
    	Array=Input;
    	Timer=60000;
    	
    }

    public void WriteIntoExcel (Table ListIn) throws Exception
    {
    	Workbook book = new XSSFWorkbook();
    	int n = 0;
    	List = ListIn;
    	if (List!=null)
    	{
    		element = List.getBegin();
    		while (List != null)
    		{
    			n++;
    			String nameSheet = String.valueOf(n);
    			Sheet sheet = book.createSheet(nameSheet);
    			int numstr = 1;
    			Row row = sheet.createRow(0);
    			Cell TimeList = row.createCell(0);
    			TimeList.setCellValue(Timer);
    			Cell NumList = row.createCell(1);
    			NumList.setCellValue(n);
    			Cell ValueList = row.createCell(2);
    			ValueList.setCellValue(List.getTableSize());
    			while (element != null)
    			{
    				row = sheet.createRow(numstr);
    				Cell CellChatID = row.createCell(0);
    				CellChatID.setCellValue(element.getChatID());
    				Cell CellNumber = row.createCell(1);
    				CellNumber.setCellValue(element.getNumber());
    				element = element.getNext();
    				numstr++;
    			}
    			List=List.getNext();
    		}
    	}
    	book.write(new FileOutputStream(file,false));  // вместо file должен быть путь к фалу
    	book.close();
    }

    public void  ReadFromExcel () throws Exception
    {
        List = null;
        Workbook ExcelBook = WorkbookFactory.create(new File(file));
//        Workbook ExcelBook = new Workbook(new POIFSFileSystem(new FileInputStream(file)));  // вместо file должен быть путь к фалу
        Iterator<Sheet> sheets = ExcelBook.sheetIterator();
        while (sheets.hasNext())
        {
            Sheet ExcelSheet = (Sheet) sheets.next();
            Iterator<Row> rows = ExcelSheet.rowIterator();
            if (rows.hasNext()) 
            {
            Row row = (Row) rows.next();
            Cell TimeList = row.getCell(0);
            Cell NumList = row.getCell(1);
            Cell ValueList = row.getCell(2);
            Array.hardAddList((int)ValueList.getNumericCellValue(),(int)TimeList.getNumericCellValue(),(int)NumList.getNumericCellValue());
//            Array.hardAddList(Integer.getInteger(TimeList.toString()),Integer.getInteger(NumList.toString()),Integer.getInteger(ValueList.toString()));
            while (rows.hasNext())
            {
                row = (Row) rows.next();
                Cell ChatID = row.getCell(0);
                Cell Number = row.getCell(1);
                if (ChatID != null && Number != null) {
                    Array.hardAdd((int)NumList.getNumericCellValue(), (int)ChatID.getNumericCellValue(),(int)Number.getNumericCellValue());
                }
            }
            }
        }
        ExcelBook.close();
    }

    public void SaveAdminData(String Login, String Password) throws Exception
    {
        Workbook AdminBook = new XSSFWorkbook();
        Sheet sheet = AdminBook.createSheet("admin");
        Row row = sheet.createRow(0);
        Cell login = row.createCell(0);
        login.setCellValue(Login);
        Cell password = row.createCell(1);
        password.setCellValue(Password);
        AdminBook.write(new FileOutputStream("filename"));
        AdminBook.close();
    }

    public String ReadLogin () throws Exception {
        HSSFWorkbook AdminBook = new HSSFWorkbook(new FileInputStream("fileName"));
        HSSFSheet sheet = AdminBook.getSheet("admin");
        HSSFRow row = sheet.getRow(0);
        HSSFCell Login = row.getCell(0);
        return Login.toString();
    }
    public String ReadPassword () throws Exception {
        HSSFWorkbook AdminBook = new HSSFWorkbook(new FileInputStream("fileName"));
        HSSFSheet sheet = AdminBook.getSheet("admin");
        HSSFRow row = sheet.getRow(0);
        HSSFCell Password = row.getCell(1);
        return Password.toString();
    }

}
