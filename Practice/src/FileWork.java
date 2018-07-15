//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//
//import java.util.*;
//
//public class FileWork
//{
//
//    private Info List;
//    private Info element;
//
//    public void WriteIntoExcel (Info ListIn) throws Exception
//    {
//        Workbook book = new XSSFWorkbook();
//        int n = 0;
//        List = ListIn;
//        element = List;
//        while (List != null)
//        {
//            n++;
//            String nameSheet = String.valueOf(n);
//            Sheet sheet = book.createSheet(nameSheet);
//            while (element != null)
//            {
//                int numstr = 0;
//                Row row = sheet.createRow(numstr);
//                Cell CellChatID = row.createCell(0);
//                CellChatID.setCellValue(element.getChatID());
//                Cell CellNumber = row.createCell(1);
//                CellNumber.setCellValue(element.getNumber());
//                element = element.getNext();
//                numstr++;
//            }
//            List.getNext();
//        }
//        book.write(new FileOutputStream(("file")));  // вместо file должен быть путь к фалу
//        book.close();
//    }
//
//    public void  ReadFromExcel (Info ListIn) throws Exception {
//        List = ListIn;
//        element = List;
//        HSSFWorkbook ExcelBook = new HSSFWorkbook(new FileInputStream("file"));  // вместо file должен быть путь к фалу
//        Iterator<Sheet> sheets = ExcelBook.sheetIterator();
//        while (sheets.hasNext())
//        {
//            HSSFSheet ExcelSheet = (HSSFSheet) sheets.next();
//            Iterator<Row> rows = ExcelSheet.rowIterator();
//            while (rows.hasNext())
//            {
//                HSSFRow row = (HSSFRow) rows.next();
//                HSSFCell ChatID = row.getCell(0);
//                HSSFCell Number = row.getCell(1);
//                if (ChatID != null && Number != null) {
//                    //добавление в очередь
//                }
//
//            }
//        }
//        ExcelBook.close();
//    }
//}
