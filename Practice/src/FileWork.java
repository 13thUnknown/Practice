import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.sql.Time;
import java.util.*;

public class FileWork
{

    private Info List;
    private Info element;

    public void WriteIntoExcel (Info ListIn) throws Exception
    {
        Workbook book = new XSSFWorkbook();
        int n = 0;
        List = ListIn;
        element = List;
        while (List != null)
        {
            n++;
            String nameSheet = String.valueOf(n);
            Sheet sheet = book.createSheet(nameSheet);
            int numstr = 1;
            Row row = sheet.createRow(0);
            Cell TimeList = row.createCell(0);
            TimeList.setCellValue(5000);
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
            List.getNext();
        }
        book.write(new FileOutputStream(("file")));  // вместо file должен быть путь к фалу
        book.close();
    }

    public void  ReadFromExcel () throws Exception {
        List = null;
        element = List;
        HSSFWorkbook ExcelBook = new HSSFWorkbook(new FileInputStream("file"));  // вместо file должен быть путь к фалу
        Iterator<Sheet> sheets = ExcelBook.sheetIterator();
        while (sheets.hasNext())
        {
            HSSFSheet ExcelSheet = (HSSFSheet) sheets.next();
            Iterator<Row> rows = ExcelSheet.rowIterator();
            HSSFRow row = (HSSFRow) rows.next();
            HSSFCell TimeList = row.getCell(0);
            HSSFCell NumList = row.getCell(1);
            HSSFCell ValueList = row.getCell(2);
            List = new List(Integer.getInteger(TimeList.toString(),Integer.getInteger(NumList.toString(),Integer.getInteger(ValueList.toString())));
            element = List;
            while (rows.hasNext())
            {
                row = (HSSFRow) rows.next();
                HSSFCell ChatID = row.getCell(0);
                HSSFCell Number = row.getCell(1);
                if (ChatID != null && Number != null) {
//                    if (element == List) {
//                        element.setChatID(Integer.getInteger(ChatID.toString()));
//                        element.setNumber(Integer.getInteger(Number.toString()));
//                    }
//                    else {
//                        element.setNext(new Info());
//                        element = element.getNext()
//                    }
                }

            }
        }
        ExcelBook.close();
    }
}
