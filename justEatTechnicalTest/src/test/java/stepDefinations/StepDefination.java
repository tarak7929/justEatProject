package stepDefinations;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefination {
	
	WebDriver driver;
	String postcode;
	WebDriverWait wait;
	
	public void openJustEatURL() {
		
		// Setup ChromeDriver
		System.setProperty("webdriver.chrome.driver", "F:\\Interview_Technical_Test\\Just_Eat_Technical_Test\\justEatTechnicalTest\\projectData\\chromedriver.exe");
		driver = new ChromeDriver();
		
		// Maximize the Browser
		driver.manage().window().maximize(); 
		// Given Implicit Wait to all WebElements
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);	
		// Explicit Wait for required WebElements
		wait = new WebDriverWait(driver,100);

		// Open JustEat URL
		driver.get("https://www.just-eat.co.uk/");	
		
	}
	
	@Given("I want food in {string}")
	public void i_want_food_in(String givenPostCode) {

		// Call OpenJustEatURL method for opening Application
		openJustEatURL();
		
		// Assigning Given Postcode from Feature file to a class level String variable
		postcode = givenPostCode;
		
	}

	@When("I search for restaurants")
	public void i_search_for_restaurants() {

		// Passing given Postcode in the application and search Restaurants for given Postcode 
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("postcode")))).sendKeys(postcode);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@aria-label=\"Search\"]")))).click();
				
	}

	@Then("I should see some restaurants in {string}")
	public void i_should_see_some_restaurants_in(String givenPostCode) {

		// Verify opening Search Page is for given Postcode from Feature file
		String searchPostCode = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"skipToMain\"]/main/div/div[1]/div[2]/div[1]/div[2]/h1")))).getText();
		Assert.assertTrue(searchPostCode.contains(givenPostCode));
		
		// Get number of Restaurants available in the given Postcode from Feature file
		String numberOfRestaurants = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"skipToMain\"]/main/div/div[2]/div[3]/h1/span[2]")))).getText();
		System.out.println(numberOfRestaurants + " are in Postcode " + givenPostCode );
		
		// Closing application & browser 
		driver.quit();
		
	}

}
