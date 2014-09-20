/* Goal: To return/retrieve data from excel application.
 */
package Utilities;
import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
public class Get_Xls {

	// ****************************************************************   1: DEFINING VARIABLES **************************************************************** //
	//Defining Variables.
	public  String filepath=null;
	public  FileInputStream file = null;
	public  HSSFWorkbook Workbook = null;
	public  HSSFSheet Sheet = null;
	public  String SheetName = null;
	public HSSFRow row   =null;
	public HSSFCell cell = null;
	public int SheetNumber=0;
	public int PhysicalNumberOfRowsInaSheet;
	
	
	// **************************************************************** 2: CONSTRUCTOR **************************************************************** //
	//CONSTRUCTOR: Creating a Constructor. 
	public Get_Xls(String filepath) {	
		this.filepath=filepath;
		try {
			file = new FileInputStream(filepath);
			Workbook = new HSSFWorkbook(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	// **************************************************************** 3: METHODS **************************************************************** //
	/* Method 1: Return RowCount. 
	   Provide: Sheetname. 
	   Returns: In a sheet, how many number of rows has physical data.
	 */
	public  int getRowCount(String SheetName)
	{
		System.out.println("************************* EXECUTING getRowCount() METHOD ******************************************************");	
		int index = Workbook.getSheetIndex(SheetName); //Defining sheets
		if(index==-1)//STEPS-1: Verifying if sheet exists by checking its index number? Sheet number starts from 0. SheetNumber given to index. So, Index starts from 0.
		{
			System.out.println("Print: Index value is -1. Means, passed sheetname not found. So returning value 0.");
			return 0;
		}
		else
		{
			HSSFSheet SheetObject = Workbook.getSheetAt(index);
			int TotalRows = SheetObject.getLastRowNum()+1;//STEPS-2: Incrementing with 1. Because in POI subject, Row number starts from 0. And user view, row number starts from 1. Since the row number passed by user, we need to add +1. And getLastRowNum() returns the last Rownumber of physical data in a sheet.
			System.out.println("Print:TotalRows in Sheet Are: "+TotalRows);
			return TotalRows;
		}
	}
	
	/* Method 2: Return ColumnCount for a Sheetname. 
	   Provide: Sheetname.
	   Returns: Returns TotalcolumnCount 
	 */
		public int getColumnCount(String SheetName)
		{
			System.out.println("************************* EXECUTING getColumnCount() METHOD ******************************************************");	
			int TotalColumnCount = -1;
			int index = Workbook.getSheetIndex(SheetName);
			if(index==-1) 
			{
				System.out.println("Print: Index value is -1. Means, passed sheetname not found. So returning empty cellvalue");
				return TotalColumnCount;
			}
			else
			{	
				HSSFSheet SheetObject = Workbook.getSheetAt(index);
				Row RowStartsAt = SheetObject.getRow(0);
				TotalColumnCount = RowStartsAt.getLastCellNum();
				System.out.println("Print: Total column count is: "+TotalColumnCount);
				return TotalColumnCount;
			}
		}
		
	/* Method 3: Return a cell value when columnNumber & RowNumber are passed. 
	   Provide: Sheetname, row number and column number.
	   Returns: Returns cell value for given rownumber & column number.
	*/
	public String getCellValue(String SheetName, int RowNumber, int ColumnNumber)
	{
		System.out.println("************************* EXECUTING getCellValue() METHOD by ROW Number & COLUMN Number ******************************************************");	
		try{
			String Result = "";
			if(RowNumber <=0)
				return "";
		int index = Workbook.getSheetIndex(SheetName);
		if(index==-1) 
		{
			System.out.println("Print: Index value is -1. Means, passed sheetname not found. So returning value 0.");
			return Result;
		}
		else
		{
			HSSFSheet SheetObject = Workbook.getSheetAt(index);
			Row RowObj = SheetObject.getRow(RowNumber-1); 
			if(RowObj == null) 
			{
				System.out.println("Print: Given Row has NULL value, hence returning empty value.");
				return Result;
			}
			
			Cell cellvalue = RowObj.getCell(ColumnNumber);
			if(cellvalue == null) 
			{
				System.out.println("Print: Given COLUMN has NULL value, hence returning empty value.");
				return Result;
			}
			
			
			if(Cell.CELL_TYPE_BLANK==cellvalue.getCellType())
			{
			    System.out.println("Print: The expected cell has blank value. Hence returning empty value.");
	            return Result;  
	        }
			
			if(cellvalue.getCellType()==Cell.CELL_TYPE_STRING) 
			{
				 Result = cellvalue.getStringCellValue(); 
				System.out.println("Print: The cell value contains a String value. Which is: "+Result);
				return Result;
			}
			if(cellvalue.getCellType()==Cell.CELL_TYPE_NUMERIC)
			{
			
				Result = String.valueOf(cellvalue.getNumericCellValue());  
				System.out.println("Print: The cell value contains a Numaric value. Which is: "+Result);
				return Result;
			}
		}	
		return Result;
	}
		catch(Exception e){
			e.printStackTrace();
			return "row "+RowNumber+" or column "+ColumnNumber +" does not exist  in xls";
		}
	}
	
	
	/* Method 4: Return a cell value when columnNAME & RowNumber are passed.
	   Provide: Workbook, Sheetname, row number and column name.
	   Returns: Returns cell value for given rownumber & column name. 
	*/
	public String getCellValue(String SheetName, int RowNumber, String ColumnName)
	{
		System.out.println("************************* EXECUTING getCellValue() METHOD by COLUMN Name & ROW Number ******************************************************");	
		try
		{
		String Nullvalue = "";
		int ColumnNumber = -1; 
		int index = Workbook.getSheetIndex(SheetName);
		if(RowNumber <=0) 
		{
			System.out.println("Print: Given RowNumber is lessthan or equal to 0. Hence returning null value.");
			return Nullvalue;
		}
		
		if(index==-1) 
		{
			System.out.println("Print: Index value is -1. Means, passed sheetname not found. So returning empty cellvalue");
			return Nullvalue;
		}
		else
		{	
			HSSFSheet SheetObject = Workbook.getSheetAt(index);
			Row RowStartsAt = SheetObject.getRow(0);
			for(int i=0; i<RowStartsAt.getLastCellNum(); i++)
			{
				String TrimmedCellValue = RowStartsAt.getCell(i).getStringCellValue().trim(); 
				String TrimmedColumnName = ColumnName.trim();
				if(TrimmedCellValue.equals(TrimmedColumnName)) 
				{
					ColumnNumber = i; 
				}
			}
			if(ColumnNumber==-1)
				return Nullvalue;
			Row MyRow = SheetObject.getRow(RowNumber-1);
			if(MyRow==null) 
			{
				System.out.println("Print: Given Row has NULL value, hence returning empty value.");
				return Nullvalue;
			}
			Cell cellvalue = MyRow.getCell(ColumnNumber);
			if(cellvalue==null) 
			{
				System.out.println("Print: The Cell contains NULL value, hence returning empty value.");
				return Nullvalue;
			}
			 
			if(Cell.CELL_TYPE_STRING==cellvalue.getCellType())
			{
				String returnvalue = cellvalue.getStringCellValue(); 
				System.out.println("Print: The cell value is String. Which is: "+returnvalue);
				return returnvalue;
			}
			if(Cell.CELL_TYPE_NUMERIC==cellvalue.getCellType())  
			{
				String returnvalue = String.valueOf(cellvalue.getNumericCellValue());
				System.out.println("Print: The cell value is String. Which is: "+returnvalue);
				return returnvalue;
			}
			if(Cell.CELL_TYPE_BLANK==cellvalue.getCellType()) 
				{
				System.out.println("Print: The cell value contianed blank value. Which is: "+Nullvalue);
				return Nullvalue;
				}
			
		}
		return Nullvalue;
		}
		catch(Exception e){
		
		e.printStackTrace();
		return "";
	}
	}
	
	
	
}
