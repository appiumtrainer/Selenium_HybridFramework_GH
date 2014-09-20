/* Goal: This class contains a method which is used to perform all Keyword related methods. 
 */
package Utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
public class KeyWords_Run {
	static KeyWords_Run k;
	Properties ObjectRepository; 
	WebDriver Driver;
	String ErrorMessage;
	
	//Method 1: Creating a private constructor. 
	private KeyWords_Run() throws IOException {
		  String filepath = ("/Users/appledev/Documents/workspace/Selenium_GitHub_HybridFramework_Original/repository/OR");  
		  ObjectRepository = new Properties();
		  try{
			 FileInputStream fs = new FileInputStream(filepath);
		     ObjectRepository.load(fs);
		  }
		  catch(Exception e)
		  {
			 System.out.println("Print: File not found");
		  }
	   }
	
	//METHOD 2: creating getInstance() method for this class. 
	  public static KeyWords_Run getInstance() throws IOException
	    {
	        if(k == null)
	            k = new KeyWords_Run();
	        return k;
	    }
	  
	  public void executeKeywords(String testcasename, Hashtable<String, String> data, Get_Xls Xls, String testsheetname) throws IOException
	  {
		  String ReturnResult = "";
	        int rows = Xls.getRowCount(testsheetname);
	        for(int rowNum = 2; rowNum <= rows; rowNum++)
	        {
	            String testCaseName = Xls.getCellValue("Test Steps", rowNum,"TCID");
	            if(testCaseName.equals(testcasename))
	            {
				 String Keyword = Xls.getCellValue(testsheetname, rowNum, "Keyword");
				 String ObjectKey = Xls.getCellValue(testsheetname,  rowNum, "Object");
				 String DataKey = Xls.getCellValue(testsheetname, rowNum, "Data");
				 String ObjectKeyValue = ObjectRepository.getProperty(ObjectKey);
				 String DataKeyValue = (String)data.get(DataKey);
				 System.out.println("Print:  keyword value is: >>>"+Keyword +": objectkey value: >>>: " +ObjectKey +": ObjectKeyValue value is: >>>: " +ObjectKeyValue +": dataKey value is: >>>: " +DataKey +": DataKeyValue value is: >>>: " +DataKeyValue); //ADDEDBY MAMTA.
	                
				 if(Keyword.equals("openBrowser"))
					 ReturnResult = openBrowser(DataKeyValue);
				 else if(Keyword.equals("navigate"))
					 ReturnResult = navigate(ObjectKeyValue);
				 else if(Keyword.equals("inputbyid"))
					 ReturnResult = inputbyid(ObjectKeyValue,DataKeyValue);
				 else if(Keyword.equals("clickByXpath"))
					 ReturnResult = clickByXpath(ObjectKeyValue);
				 else if(Keyword.equals("close"))
					 ReturnResult = close();
	 
				 if(ReturnResult=="Fail") {
		                File scrFile = ((TakesScreenshot)Driver).getScreenshotAs(OutputType.FILE);
		                String str=String.valueOf(System.currentTimeMillis());
		    			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"\\screenshots\\"+ testcasename+str+".png"));
		    			Assert.assertNull(ReturnResult, ErrorMessage);
				 }
			 }
		  }
	  }

	
	
	//3: To launch a browser.
	public String openBrowser(String Object)
	  {
		 String ReturnResult = "Fail";
		 try
		  {
			  if(Object.equals("Mozilla"))
		  	  {
				  System.setProperty("webdriver.firefox.bin","/Users/appledev/Downloads/Never_Remove/Firefox/Firefox");	
				  Driver  = new FirefoxDriver();
				  ReturnResult = "pass";
				  System.out.println("Print: Passed: openBrowser method() has passed");
				  return ReturnResult;
			  }
			  if(Object.equals("Chrome"))
		  	  {
				  System.setProperty("webdriver.chrome.driver", "/Users/appledev/Documents/workspace/Selenium_GitHub_HybridFramework/lib/chromedriver.exe");
		          Driver = new ChromeDriver();
				  ReturnResult = "pass";
				  System.out.println("Print: Passed: openBrowser method() has passed");
				  return ReturnResult;
			  }
			  if(Object.equals("IE"))
		        {
		            System.setProperty("webdriver.ie.driver", "/Users/appledev/Downloads/IEdriver.exe");
		            Driver = new InternetExplorerDriver();
		            ReturnResult = "pass";
					System.out.println("Print: Passed: openBrowser method() has passed");
					return ReturnResult;
		        }
		        Driver.manage().timeouts().implicitlyWait(15L, TimeUnit.SECONDS);
		       return ReturnResult;
		  }
		  catch(Exception e)
		  {
			  ErrorMessage=e.getMessage();
			  System.out.println(ErrorMessage);
			  System.out.println("Print: ErrorMessage is: "+ErrorMessage);
			 return ReturnResult;
		  }
		  
	  }
	  
	//4: To navigate to URL.
	  public String navigate(String Object)
	  {
		  String ReturnResult = "Fail";
		  try
		  {
			  Driver.get(Object);
			  System.out.println("Print: Passed: navigate method() has passed");
			  ReturnResult = "Pass";
			  return ReturnResult;
		  }
		  catch(Exception e)
		  {
			  ErrorMessage = e.getMessage();
			  System.out.println("Print: ErrorMessage is: "+ErrorMessage);
			  System.out.println();
			  return ReturnResult;
		  }
		  
	  }
	  
	  //5: Using ID Locator: enter keyword in a htmlelement.
	  public String inputbyid(String Object1, String Object2)
	  {
		  String ReturnResult = "Fail";
		  try{
			  Driver.findElement(By.id(Object1)).sendKeys(Object2);
		  	  System.out.println("Print: Passed: inputbyid method() has passed");
		  	 ReturnResult = "Pass";
		  	  return  ReturnResult;
		  }
		  catch(Exception e)
		  {
			  ErrorMessage=e.getMessage();
			  System.out.println("Print: ErrorMessage is: "+ErrorMessage);
			  return  ReturnResult;
		  }
	  }
	  
	  //Using Xpath locator: Click on HTML element.
	  public String clickByXpath(String Object)
	  {
		  String ReturnResult = "Fail";
		  try{
			  Driver.findElement(By.xpath(Object)).click();
		  	  System.out.println("Print: Passed: clickByXpath method() has passed");
		 	 ReturnResult = "Pass";
		  	return  ReturnResult;
		  	}
		  catch(Exception e)
		  	{
			  ErrorMessage=e.getMessage();
			  System.out.println(ErrorMessage);
			  System.out.println("Print: ErrorMessage is: "+ErrorMessage);
			  return  ReturnResult;
		  	}
	  }
	  
	  //Using Xpath locator: Click on HTML element.
	  public String close()
	  {
		  String ReturnResult = "Fail";
		  try{
			  Driver.close();
		  	  System.out.println("Print: Passed: close method() has passed");
		 	 ReturnResult = "Pass";
		  	return  ReturnResult;
		  	}
		  catch(Exception e)
		  	{
			  ErrorMessage=e.getMessage();
			  System.out.println(ErrorMessage);
			  System.out.println("Print: ErrorMessage is: "+ErrorMessage);
			  return  ReturnResult;
		  	}
	  }
}
