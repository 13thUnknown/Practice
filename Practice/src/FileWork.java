//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//
//import java.util.Iterator;
//
//public class FileWork
//{
//
//    private ListArray List;
//    private Info element;
//    private String file;
//    
//    FileWork() {
//    	List = null;
//    	element = null;
//    	file = "D://Bot_bd.xlsx";
//    }
//
//    public void WriteIntoExcel (ListArray ListIn) throws Exception
//    {
//        Workbook book = new XSSFWorkbook();
//        int n = 0;
//        List = ListIn;
//        element = List.getBegin().getBegin();
//        while (List != null)
//        {
//            n++;
//            String nameSheet = String.valueOf(n);
//            Sheet sheet = book.createSheet(nameSheet);
//            int numstr = 1;
//            Row row = sheet.createRow(0);
//            Cell TimeList = row.createCell(0);
//            TimeList.setCellValue(5000);
//            Cell NumList = row.createCell(1);
//            NumList.setCellValue(n);
//            Cell ValueList = row.createCell(2);
//            ValueList.setCellValue(List.getTableSize(n));
//            while (element != null)
//            {
//                row = sheet.createRow(numstr);
//                Cell CellChatID = row.createCell(0);
//                CellChatID.setCellValue(element.getChatID());
//                Cell CellNumber = row.createCell(1);
//                CellNumber.setCellValue(element.getNumber());
//                element = element.getNext();
//                numstr++;
//            }
//            List.getNext();
//        }
//        book.write(new FileOutputStream("D:\\Bot_bd.xlsx", false));  // вместо file должен быть путь к фалу
//        book.close();
//    }
//
//    public void  ReadFromExcel () throws Exception
//    {
//        List = null;
//        XSSFWorkbook ExcelBook = new XSSFWorkbook(new FileInputStream(file));  // вместо file должен быть путь к фалу
//        Iterator<Sheet> sheets = ExcelBook.sheetIterator();
//        while (sheets.hasNext())
//        {
//        	XSSFSheet ExcelSheet = (XSSFSheet) sheets.next();
//            Iterator<Row> rows = ExcelSheet.rowIterator();
//            XSSFRow row = (XSSFRow) rows.next();
//            XSSFCell TimeList = row.getCell(0);
//            XSSFCell NumList = row.getCell(1);
//            XSSFCell ValueList = row.getCell(2);
//            List.hardAddList(Integer.getInteger(TimeList.toString()),Integer.getInteger(NumList.toString()),Integer.getInteger(ValueList.toString()));
//            while (rows.hasNext())
//            {
//                row = (XSSFRow) rows.next();
//                XSSFCell ChatID = row.getCell(0);
//                XSSFCell Number = row.getCell(1);
//                if (ChatID != null && Number != null) {
//                    List.hardAdd(Integer.getInteger(NumList.toString()), Long.getLong(ChatID.toString()),Integer.getInteger(Number.toString()));
//                }
//            }
//        }
//        ExcelBook.close();
//    }
//
//    public void SaveAdminLogin(String Login) throws Exception
//    {
//        Workbook AdminBook = new XSSFWorkbook();
//        Sheet sheet = AdminBook.createSheet("admin");
//        Row row = sheet.createRow(0);
//        Cell login = row.createCell(0);
//        login.setCellValue(Login);
//        AdminBook.write(new FileOutputStream("D:\\Bot_bd.xlsx", false));
//        AdminBook.close();
//    }
//    
//    public void SaveAdminPassword(String Password) throws Exception
//    {
//        Workbook AdminBook = new XSSFWorkbook();
//        Sheet sheet = AdminBook.createSheet("admin");
//        Row row = sheet.createRow(0);
//        Cell password = row.createCell(1);
//        password.setCellValue(Password);
//        AdminBook.write(new FileOutputStream("D:\\Bot_bd.xlsx", false));
//        AdminBook.close();
//    }
//
//    public String ReadLogin () throws Exception {
//        XSSFWorkbook AdminBook = new XSSFWorkbook(new FileInputStream("D:\\Bot_bd.xlsx"));
//        XSSFSheet sheet = AdminBook.getSheet("admin");
//        XSSFRow row = sheet.getRow(0);
//        XSSFCell Login = row.getCell(0);
//        return Login.toString();
//    }
//    public String ReadPassword () throws Exception {
//        XSSFWorkbook AdminBook = new XSSFWorkbook(new FileInputStream("D:\\Bot_bd.xlsx"));
//        XSSFSheet sheet = AdminBook.getSheet("admin");
//        XSSFRow row = sheet.getRow(0);
//        XSSFCell Password = row.getCell(1);
//        return Password.toString();
//    }
//
//}
