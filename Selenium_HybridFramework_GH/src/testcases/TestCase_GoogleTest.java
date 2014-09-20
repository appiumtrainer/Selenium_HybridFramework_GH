/*  1: Goal: The framework starts here. It is a testcase file. In this, 
 * 	- 1: Created a WebDriver
 *   -2: Navigated to www.google.com 
 *   -3: Entered Keyword in Search TextBox
 *   -4: Clicked on Search button.
 * */
package testcases;
import java.io.IOException;
import java.util.Hashtable;
import org.testng.SkipException;
import org.testng.annotations.Test;
import Utilities.DataProviderAnnotation;
import Utilities.KeyWords_Run;
import Utilities.TestData_Utilities;
public class TestCase_GoogleTest extends DataProviderAnnotation {

	static final String GoogleTestVariable = "GoogleTest";

	public TestCase_GoogleTest() 
	{
		super(GoogleTestVariable);
		System.out.println("Print: This is a GooglTest Class Constructor: "+GoogleTestVariable);
	}
	
	@Test(dataProvider="DataProvider_GetDataFromExcelSheet")
	public void GoogleTestCaseExeuction(Hashtable<String,String> data) throws IOException
	{
		if (!TestData_Utilities.isExecutableFromTestCasesSheet(Xls, "Test Cases", "GoogleTest"))
	   throw new SkipException("Runmode for test is set to NO");
	
	   if (!data.get("Runmode").equals("Y")) 
	   throw new SkipException("Runmode for test data set is set to NO");
	
	   KeyWords_Run kw=KeyWords_Run.getInstance();
	   kw.executeKeywords(GoogleTestVariable, data, Xls, "Test Steps");
	}
}
