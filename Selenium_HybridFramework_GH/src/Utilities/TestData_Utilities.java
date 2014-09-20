/* Goal: This file have two methods.
 * isExecutableFromTestCasesSheet : Verifies in Test Cases Sheet: It verifies, if Runmode set to "Y"
 * returnTestData : Retrieve Test Data in "Key Value" parameter from TestData Sheet for a given TestCase
 * */

package Utilities;
import java.util.Hashtable;
public class TestData_Utilities {
public static String dataSheet = "Test Data";
	
/*Goal: Verifies in Test Cases Sheet: It verifies, if Runmode set to "Y". 
Return type: Boolean. True/False.
Parameters: XLS file, Sheet name & Testcase name.
*/
	public static boolean isExecutableFromTestCasesSheet(Get_Xls xls, String dataSheet, String testcase)
	{
	int totalRows = 0;
	totalRows = xls.getRowCount(dataSheet);
	
	int GetRowNum = 0;
	for(int RowNum=2; RowNum<=totalRows; RowNum++)
		if(xls.getCellValue(dataSheet,RowNum, "TCID").equals(testcase))
			GetRowNum = RowNum;
			boolean value;
			value = xls.getCellValue(dataSheet, GetRowNum, "Runmode").equals("Y");
			return value; 
	}

//Method 2: Retrieve Test Data in an Object with two dimensionalarray from TestData Sheet for a given TestCase
/* Goal: Retrieve Test Data in "Key Value" parameter from TestData Sheet for a given TestCase.
 * ReturnType: An objecttype variables with twodimensional array. 
 */
public static Object[][] returnTestData(Get_Xls Xls, String testcase)
{
	int TestStepsStartATRowNumber=0; 
	int totalRowsInaSheet = Xls.getRowCount(dataSheet); 
	System.out.println("Print: Number of rows in a sheet are: "+totalRowsInaSheet);
	
	for(int RowNum=1; RowNum<totalRowsInaSheet; RowNum++)
	{
		if(!(Xls.getCellValue(dataSheet,RowNum, 0).equals(testcase))) 
		continue; 							
		TestStepsStartATRowNumber = RowNum;  
		break;								
	}
	System.out.println("Test Steps Starts At RowNumber: "+TestStepsStartATRowNumber);
	
	int ColumnStartsAtRowNumber = TestStepsStartATRowNumber+1;  
	int TestDataStartsAtRowNumber= TestStepsStartATRowNumber+2; 
	System.out.println("Print: ColumnStartsAtRowNumber value is: "+ColumnStartsAtRowNumber);
	System.out.println("Print: TestDataStartsAtRowNumber value is: "+TestDataStartsAtRowNumber);
	
	int TotalColumns_WhohaveTestdata;
	for(TotalColumns_WhohaveTestdata=0; !(Xls.getCellValue(dataSheet, ColumnStartsAtRowNumber,TotalColumns_WhohaveTestdata)).equals(""); TotalColumns_WhohaveTestdata++);
	System.out.println("Print: TotalColumns_WhohaveTestdata value is : "+TotalColumns_WhohaveTestdata);
	
	int TotalRows_WhohaveTestdata;
	for(TotalRows_WhohaveTestdata=0; !(Xls.getCellValue(dataSheet, TestDataStartsAtRowNumber+TotalRows_WhohaveTestdata, 0 ).equals("")); TotalRows_WhohaveTestdata++);
	System.out.println("Print: TotalRows_WhohaveTestdata value is: "+TotalRows_WhohaveTestdata);
	
	Hashtable<String, String> table = null;
	Object testdata[][] = new Object[TotalRows_WhohaveTestdata][1];
	int x=0;
	
	for(int rNum=TestDataStartsAtRowNumber; rNum<TestDataStartsAtRowNumber+TotalRows_WhohaveTestdata; rNum++)
		{
		table =new Hashtable<String, String>();
		for(int cNum=0; cNum<TotalColumns_WhohaveTestdata; cNum++)
			{
				String data = Xls.getCellValue(dataSheet, rNum, cNum);
	    		table.put(Xls.getCellValue(dataSheet, ColumnStartsAtRowNumber, cNum), data);
			}
		testdata[x][0] = table;
		System.out.println("Print: Test Data is: "+testdata[x][0]);
		x++;
		}
	return testdata;
	}

}


