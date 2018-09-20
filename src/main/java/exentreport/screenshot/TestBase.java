package exentreport.screenshot;



	import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

	import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

		import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

		public class TestBase {
			
			public static ExtentReports extent;
			public static ExtentTest test;
			public static WebDriver driver;
			
			static {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
				
				extent = new ExtentReports(System.getProperty("user.dir") + "/src/main/java/report/test" + formater.format(calendar.getTime()) + ".html", false);
				
				
			}
			
			public void highLighterMethod(WebDriver driver, WebElement element){
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
			}
			
	public String getScreenShot(String imagename) throws IOException{
		if(imagename.equals("")){
			imagename="blank";
		}
		File image=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String imagelocation=System.getProperty("user.dir")+"/src/main/java/screenshot/";
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat formater=new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String actualimagename=imagelocation+imagename+"_"+formater.format(calendar.getTime())+".png";
		File destFile=new File(actualimagename);
		FileUtils.copyFile(image, destFile);
		return actualimagename;
	}
	@Test
    public void captureScreenshot() throws IOException, InterruptedException
    {
        //test = extent.startTest("captureScreenshot");
        System.setProperty("webdriver.chrome.driver","C:\\Users\\rajesh\\Desktop\\chomlat\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://sqanxconnweb.nxstage.com/Dashboard/(S(aupxfdqlbty3r5aapvapumxj))/Account/Login.aspx?ReturnUrl=%2fDashboard%2f");
       WebElement center=driver.findElement(By.xpath("//*[@id='MainContent_CenterId']"));
       highLighterMethod(driver,center);
       center.sendKeys("900915");
        test.log(LogStatus.PASS, "center entered");
        String screen=getScreenShot("");
		test.log(LogStatus.PASS, test.addScreenCapture(screen));
		
		WebElement user=driver.findElement(By.xpath("//*[@id='MainContent_UserID']"));
		highLighterMethod(driver,user);
		user.sendKeys("nurse15adm@nxstage.com");
		test.log(LogStatus.PASS, "user entered");
		String screen1=getScreenShot("");
		test.log(LogStatus.PASS, test.addScreenCapture(screen1));
		
		WebElement pass=driver.findElement(By.xpath("//*[@id='MainContent_Password']"));
		highLighterMethod(driver,pass);
		pass.sendKeys("B@home4Tx");
		test.log(LogStatus.PASS, "password entered");
		String screen2=getScreenShot("");
		test.log(LogStatus.PASS, test.addScreenCapture(screen2));
		Thread.sleep(3000);
		
		
		WebElement login=driver.findElement(By.xpath("//*[@id='MainContent_LoginButton']"));
		highLighterMethod(driver,pass);
		login.click();
		test.log(LogStatus.PASS, "password entered");
		String screen3=getScreenShot("");
		test.log(LogStatus.PASS, test.addScreenCapture(screen3));
		Thread.sleep(3000);
		
		
		WebElement logout=driver.findElement(By.xpath("//*[@id='btnLogout']"));
		highLighterMethod(driver,pass);
		logout.click();
		test.log(LogStatus.PASS, "logout scucessfully");
		String screen4=getScreenShot("");
		test.log(LogStatus.PASS, test.addScreenCapture(screen4));
		
		
    }
			
			public void getresult(ITestResult result) throws IOException {
				if (result.getStatus() == ITestResult.SUCCESS) {
					test.log(LogStatus.PASS, result.getName() + " test is pass");
					String screen=getScreenShot("");
					test.log(LogStatus.PASS, test.addScreenCapture(screen));
				} else if (result.getStatus() == ITestResult.SKIP) {
					test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
				} else if (result.getStatus() == ITestResult.FAILURE) {
					test.log(LogStatus.ERROR, result.getName() + " test is failed" + result.getThrowable());
					String screen=getScreenShot("");
					test.log(LogStatus.FAIL, test.addScreenCapture(screen));
				} else if (result.getStatus() == ITestResult.STARTED) {
					test.log(LogStatus.INFO, result.getName() + " test is started");
				}
			}

			@AfterMethod()
			public void afterMethod(ITestResult result) throws IOException {
				getresult(result);
			}

			
			@BeforeMethod()
			public void beforeMethod(Method result) {
				test = extent.startTest(result.getName());
				test.log(LogStatus.INFO, result.getName() + " test Started");
			}

			@AfterClass(alwaysRun = true)
			public void endTest() {
				extent.endTest(test);
				extent.flush();
			}

	

		

	}


