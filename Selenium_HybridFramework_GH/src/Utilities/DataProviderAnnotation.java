/* Goal: Why this utility class is required?  In this class, we create @DataProvider and we perform/forward "Key Value"/Test Data from Utility classes to TestCase (GoogleTest) class.*/
package Utilities;
import org.testng.annotations.DataProvider;
public class DataProviderAnnotation {
	
	//1: Creating an object for Get_Xls's constructor. 
	public static Get_Xls Xls = new Get_Xls("/Users/appledev/Documents/workspace/Selenium_GitHub_HybridFramework_Original/testexecution/TestCases2.xls");
	public String BaseClassVariable;
	
	//2: Constructor.  
	public DataProviderAnnotation(String BaseClassConstructorVariable) 
	{
		this.BaseClassVariable=BaseClassConstructorVariable;
		System.out.println("Print: This is a BaseClass Constructor."+this.BaseClassVariable);
	}
	
	//3: @DataProvider Annotation
	@DataProvider
	public Object[][]DataProvider_GetDataFromExcelSheet() { //Calls from TestUtil	
	return TestData_Utilities.returnTestData(Xls,this.BaseClassVariable);
	}
}