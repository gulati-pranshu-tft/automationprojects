package pageScript;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageLevelSelectors.amazonPageSelectors;

public class amazonProductPage {
	WebDriver driver;
	
	private amazonPageSelectors s1 = new amazonPageSelectors();
	public void launchBrowser()
	{
		WebDriverManager.chromedriver().setup();
		 driver = new ChromeDriver();
		driver.get("https://www.amazon.com/");
	}
	public  List<String> productsDropdown(String searchText) 
	{
		
		driver.findElement(s1.searchBox).sendKeys(searchText);
		driver.findElement(s1.searchIcon).click();
		//Thread.sleep(5000);
		List<WebElement> l1 = driver.findElements(s1.productDescription);
		List<String> s1l = new ArrayList<String>();
		for(int i=2;i<4;i++)
		{
			String s1 = l1.get(i).getText();
		s1l.add(s1);
		}
		driver.findElement(s1.searchBox).clear();
		
		return s1l;
	}
	
	public void closeBrowser()
	{
		driver.quit();
	}
}
