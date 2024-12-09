package O_DDF_FDCalculator;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import O_DDF_Nop_commerce.AXLUtility;
import O_DDF_Nop_commerce.AXLUtility;
import P_DDF1_Github.XLUtility;

public class DataDrivenTest_DCalculator {

	WebDriver driver; 
	
	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "D:\\\\drivers\\\\chromedriver-win64\\\\chromedriver-win64\\\\chromedriver.exe");
		driver= new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize(); 
	}
	
	@Test (dataProvider="LoginData")
	public void CalulationTest(String princ, String roi, String per, String exp_mvalue) throws InterruptedException {
		driver.get("https://www.moneycontrol.com/fixed-income/calculator/state-bank-of-india/fixed-deposit-calculator-SBI-BSB001.html?classic=true");

		Thread.sleep(2000);
		try {
		driver.findElement(By.xpath("//button[@class='No thanks']")).click();
		}
		catch(Exception e) {
			System.out.println("Go forward");
		}
		Thread.sleep(2000);
		
		WebElement pa = driver.findElement(By.xpath("//input[@id='principal']"));
	    pa.clear();
	    pa.sendKeys(princ);
	    
	    Thread.sleep(2000);
	    
	    WebElement interest = driver.findElement(By.xpath("//input[@id='interest']"));
	    interest.clear();
	    interest.sendKeys(roi); 
	    
	    Thread.sleep(2000);
	    
	    WebElement tenure = driver.findElement(By.xpath("//input[@id='tenure']"));
	    tenure.clear();
	    tenure.sendKeys(per);
	    
	    Thread.sleep(2000);
	    
	    WebElement tenper = driver.findElement(By.xpath("//select[@id='tenurePeriod']"));
	    Select s = new Select(tenper);
	    s.selectByVisibleText("year(s)");
	    
	    Thread.sleep(2000);
	    
	    WebElement fre = driver.findElement(By.xpath("//select[@id='frequency']"));
	    //Select s1 = new Select(tenper);
	    //s1.selectByVisibleText("Simple Interest"); 
	    
	    Actions act = new Actions(driver);
	    act.click(fre).perform();
	    act.sendKeys(Keys.ARROW_UP).perform();
	    act.sendKeys(Keys.ARROW_UP).perform();
	    act.sendKeys(Keys.ENTER).perform(); 
	    
	    Thread.sleep(2000);
	    
	    WebElement calculate = driver.findElement(By.xpath("//img[@src='https://images.moneycontrol.com/images/mf_revamp/btn_calcutate.gif']"));
	    calculate.click();
	    
	    Thread.sleep(2000);
	    
	    String act_mvalue = driver.findElement(By.xpath("//span[@id='resp_matval']//strong")).getText();
	    
	    System.out.println(act_mvalue);
	    System.out.println(exp_mvalue);
	    
	    if(exp_mvalue.equals(act_mvalue)) { 
	    	
	    	Assert.assertTrue(true); 
	    		
	    }
	    else {
	    	
	    	Assert.assertTrue(false);
	    } 
	    
	    //driver.quit(); 
	    Thread.sleep(2000);
	    
} 
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException {
		
		String path = "D:\\Testing Class Notes\\caldata2.xlsx"; 
		XLUtility xlutil = new XLUtility(path);
		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1",1);
		
		String loginData[][]=new String[totalrows][totalcols]; 
		
		for(int i=1;i<=totalrows;i++) 
		 {
			for(int j=0;j<totalcols;j++) 
			{
				loginData[i-1][j] = xlutil.getCellData("Sheet1", i, j);
			}
		}
		
		return loginData;  
} 
	
	

@AfterClass 
void TearDown() throws InterruptedException { 
	//driver.close(); 
    Thread.sleep(2000);
}

	
}
