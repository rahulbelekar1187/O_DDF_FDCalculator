package O_DDF_FDCalculator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;   // UI of the report
	public ExtentReports extent;  // populate common info on the report
	public ExtentTest test;        // creating test case entries in the report & update status of the test methods
	
	public void onStart(ITestContext context) {
		
		//String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp 
		//String repName="Test-Report-"+timestamp+".html";
		
		sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+ "/reports/myReport1.html");  // specify location
		sparkReporter.config().setDocumentTitle("Automation Report"); // title of report
		sparkReporter.config().setReportName("Functional Testing"); //name of the report
		sparkReporter.config().setTheme(Theme.STANDARD);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Host Name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Tester Nmae", "pavan");
		extent.setSystemInfo("os", "Windows10");
		extent.setSystemInfo("Browser Name", "Chrome");
		
	} 
	
	public void onTestSuccess(ITestResult result) {
		test=extent.createTest(result.getName()); // create new entry in the report
		test.log(Status.PASS, "Test Case PASSED is:" + result.getName());  // Update status p/f/s
	}
	
	public void onTestFailure(ITestResult result) {
		test=extent.createTest(result.getName()); // create new entry in the report
		test.log(Status.FAIL, "Test Case FAILED is:" + result.getName()); 
		test.log(Status.FAIL, "Test Case FAILED cause is:" + result.getThrowable());
	}
	
	public void onTestSkipped(ITestResult result) {
		test=extent.createTest(result.getName()); // create new entry in th report
		test.log(Status.SKIP, "Test Case SKIPPED is:" + result.getName());
	}
	
	public void onFinish(ITestContext testContext) {
		extent.flush();
	}

}

